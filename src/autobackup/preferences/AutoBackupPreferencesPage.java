package autobackup.preferences;

/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *   
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
