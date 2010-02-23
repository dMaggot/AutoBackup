package autobackup.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import autobackup.Activator;

public class AutoBackupPreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{
	private DirectoryFieldEditor localPath;
	private BooleanFieldEditor useRemote;
	private RadioGroupFieldEditor remoteService;
	private StringFieldEditor user;
	private StringFieldEditor hostname;
	private StringFieldEditor remotePath;

	public AutoBackupPreferencesPage()
	{
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("AutoBackup Plugin Preferences");
	}

	public void createFieldEditors()
	{
		localPath = new DirectoryFieldEditor(AutoBackupPreferenceNames.P_LOCALPATH, "Local Path to Store Backups: ", getFieldEditorParent());
		useRemote = new BooleanFieldEditor(AutoBackupPreferenceNames.P_USEREMOTE, "Use &Remote Backup: ", getFieldEditorParent());
		remoteService = new RadioGroupFieldEditor(AutoBackupPreferenceNames.P_SERVICE, "Select your Backup Service", 1, new String[][] { { "SCP", "ssh" } }, getFieldEditorParent());
		user = new StringFieldEditor(AutoBackupPreferenceNames.P_USER, "&User:", getFieldEditorParent());
		hostname = new StringFieldEditor(AutoBackupPreferenceNames.P_HOSTNAME, "&Hostname:", getFieldEditorParent());
		remotePath = new StringFieldEditor(AutoBackupPreferenceNames.P_REMOTEPATH, "Remote Path to Store Backups: ", getFieldEditorParent());

		addField(localPath);
		addField(useRemote);
		addField(remoteService);
		addField(user);
		addField(hostname);
		addField(remotePath);
	}

	public void init(IWorkbench workbench)
	{
	}
}
