package autobackup;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import org.osgi.util.tracker.ServiceTracker;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jsch.core.*;
import org.eclipse.jsch.ui.UserInfoPrompter;

import autobackup.preferences.AutoBackupPreferenceNames;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.zip.*;
import java.util.ArrayList;

public class AutoBackupParticipant implements ISaveParticipant
{
	private IPreferenceStore preferences;

	@Override
	public void doneSaving(ISaveContext context)
	{
		preferences = Activator.getDefault().getPreferenceStore();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		ArrayList<String> zipfiles = new ArrayList<String>();

		try
		{
			for (IProject i : projects)
			{
				File projectFolder = new File(i.getLocation().toString());
				String zipFileName = preferences.getString(AutoBackupPreferenceNames.P_LOCALPATH) + "/" + i.getName() + ".zip";
				FileOutputStream zipFile = new FileOutputStream(zipFileName);
				ZipOutputStream projectGZIP = new ZipOutputStream(zipFile);

				zipfiles.add(zipFileName);
				compressFile(projectGZIP, projectFolder, projectFolder.getParent());

				projectGZIP.close();
				zipFile.close();
			}

			if (preferences.getBoolean(AutoBackupPreferenceNames.P_USEREMOTE))
			{
				String service = preferences.getString(AutoBackupPreferenceNames.P_SERVICE);

				if (service == "scp")
				{
					doScp(zipfiles);
				}
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void prepareToSave(ISaveContext context) throws CoreException
	{
	}

	@Override
	public void rollback(ISaveContext context)
	{
	}

	@Override
	public void saving(ISaveContext context) throws CoreException
	{
	}

	public void compressFile(ZipOutputStream zipfile, File file, String headPath)
	{
		if (!file.isDirectory())
		{
			byte[] data = new byte[2048];
			ZipEntry entry = new ZipEntry(file.getAbsolutePath().substring(headPath.length() + 1));

			try
			{
				FileInputStream fileStream = new FileInputStream(file.getAbsolutePath());
				BufferedInputStream origin = new BufferedInputStream(fileStream, 2048);

				zipfile.putNextEntry(entry);

				int count;
				while ((count = origin.read(data, 0, 2048)) != -1)
				{
					zipfile.write(data, 0, count);
				}

				zipfile.flush();
				zipfile.closeEntry();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} else
		{
			for (String s : file.list())
				compressFile(zipfile, new File(file.getAbsolutePath() + "/" + s), headPath);
		}
	}

	public void doScp(ArrayList<String> filenames)
	{
		IJSchService sshService = (IJSchService) Activator.getDefault().getSshServiceTracker().getService();
		Session sshSession;
		try
		{
			sshSession = sshService.createSession(preferences.getString(AutoBackupPreferenceNames.P_HOSTNAME), 22, preferences.getString(AutoBackupPreferenceNames.P_USER));

			UserInfoPrompter sshInfoPrompter = new UserInfoPrompter(sshSession);
			sshSession.connect();

			for (String z : filenames)
			{
				File zFile = new File(z);
				String zFileName = preferences.getString(AutoBackupPreferenceNames.P_REMOTEPATH) + "/" + zFile.getName();
				Channel channel = sshSession.openChannel("exec");
				OutputStream out = channel.getOutputStream();
				InputStream in = channel.getInputStream();
				String command = "scp -t " + zFileName;
				FileInputStream fis = new FileInputStream(z);

				((ChannelExec) channel).setCommand(command);

				channel.connect();

				if (in.read() != 0)
				{
					System.exit(0);
				}

				// send "C0644 filesize filename", where filename should not
				// include '/'
				long filesize = zFile.length();
				command = "C0644 " + filesize + " ";

				if (zFileName.lastIndexOf('/') > 0)
				{
					command += zFileName.substring(zFileName.lastIndexOf('/') + 1);
				} else
				{
					command += zFileName;
				}

				command += "\n";

				out.write(command.getBytes());
				out.flush();

				if (in.read() != 0)
				{
					System.exit(0);
				}

				// send a content of lfile
				byte[] buf = new byte[1024];
				while (true)
				{
					int len = fis.read(buf, 0, buf.length);
					if (len <= 0)
						break;
					out.write(buf, 0, len); // out.flush();
				}
				fis.close();

				// send '\0'
				buf[0] = 0;
				out.write(buf, 0, 1);
				out.flush();

				if (in.read() != 0)
				{
					System.exit(0);
				}
				out.close();

				channel.disconnect();
			}

			sshSession.disconnect();
		} catch (JSchException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
