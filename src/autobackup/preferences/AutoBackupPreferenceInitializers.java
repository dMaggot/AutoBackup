package autobackup.preferences;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import autobackup.Activator;

public class AutoBackupPreferenceInitializers extends AbstractPreferenceInitializer
{

	@Override
	public void initializeDefaultPreferences()
	{		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
	store.setDefault(AutoBackupPreferenceNames.P_LOCALPATH, ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString());
	store.setDefault(AutoBackupPreferenceNames.P_USEREMOTE, true);
	store.setDefault(AutoBackupPreferenceNames.P_SERVICE, "ssh");
	store.setDefault(AutoBackupPreferenceNames.P_USER, "");
	store.setDefault(AutoBackupPreferenceNames.P_HOSTNAME, "");
		store.setDefault(AutoBackupPreferenceNames.P_REMOTEPATH, "");
	}

}
