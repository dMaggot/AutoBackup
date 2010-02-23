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
