package autobackup;

import org.eclipse.jsch.core.IJSchService;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class Activator extends AbstractUIPlugin
{
	public static final String PLUGIN_ID = "AutoBackup";
	private static Activator plugin;
	private static ServiceTracker sshServiceTracker;

	public Activator()
	{
	}

	public void start(BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;
		sshServiceTracker = new ServiceTracker(context, IJSchService.class.getName(), null);
		sshServiceTracker.open();
	}

	public void stop(BundleContext context) throws Exception
	{
		plugin = null;
		sshServiceTracker.close();
		super.stop(context);
	}

	public static Activator getDefault()
	{
		return plugin;
	}

	public ServiceTracker getSshServiceTracker()
	{
		return sshServiceTracker;
	}
}
