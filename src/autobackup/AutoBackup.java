package autobackup;

import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IStartup;

public class AutoBackup implements IStartup
{
	@Override
	public void earlyStartup()
	{
		ISaveParticipant autoBackupParticipant = new AutoBackupParticipant();

		try
		{
			ResourcesPlugin.getWorkspace().addSaveParticipant(Activator.getDefault(), autoBackupParticipant);
		} catch (CoreException e)
		{
			e.printStackTrace();
		}
	}
}
