# Introduction #

The following steps are needed to create your own custom FBPwn plugin


# Details #

FBPwn uses Java reflection API to load custom plugins at startup, so with basic knowledge about Java, you can make your own plugins.

  * You'll need to download the latest jar executable file, and include it in your classpath while compiling and building your plugin, alternatively you can checkout the latest code and include it in your project along with the necessary libraries.
  * Your plugin should be placed in the following package: `fbpwn.plugins.core`
  * Any additional needed GUI components should be placed in the following package: `fbpwn.plugins.ui`
  * Your plugin should:
    * Extend `FacebookTask` class.
    * Provide a constructor as indicated below in the [SamplePlugin](#SamplePlugin.md).
    * Override `public void init()` and place any initialization needed when placing the plugin in the execution queue.
    * Override `public boolean run()` and place your logic here. This method should return false to be retried again by the execution queue, and return true to indicate the end of execution. You'll also need to check for the cancel flag, and report the status of the plugin while executing.
    * Override `public String toString()` and provide the name of the plugin that will appear in the execution queue.
  * After compiling and building your plugin, you should move the .class files beside the jar file inside their appropriate directories.
  * Don't forget to tell us about your plugin through the [discussion group](http://groups.google.com/group/fbpwn-dev), we'll include it in the next release.

# SamplePlugin #
```

package fbpwn.plugins.core;

import fbpwn.core.AuthenticatedAccount;
import fbpwn.core.FacebookAccount;
import fbpwn.ui.FacebookGUI;
import fbpwn.core.FacebookTask;
import fbpwn.core.FacebookTaskState;
import java.io.File;

/**
 * Represents a sample plugin  
 */
public class SamplePlugin extends FacebookTask {

    public SamplePlugin(FacebookGUI FacebookGUI,
            FacebookAccount facebookProfile,
            AuthenticatedAccount authenticatedProfile,
            File workingDirectory) {
        super(FacebookGUI, facebookProfile, authenticatedProfile, workingDirectory);
    }

    @Override
    public void init() {
        // Put your intialization here
        // Will be called once the plugin is inserted in the execution queue
        // This method is called from the event-dispatcher thread
        // You can add additional forms and dialogs to recieve more input
        // from the user.
    }

    @Override
    public boolean run() {
        // Do Logic here and update status accordingly
        // Will be called when the plugin gets scheduled

        Logger.getLogger(SamplePlugin.class.getName()).log(Level.INFO, "Debug information"); 

        setTaskState(FacebookTaskState.Running);
        setMessage("Processing...");
        setPercentage(0.0);
        getFacebookGUI().updateTaskProgress(this);


        for (int i = 0; i < 100; i++) {
            setPercentage((double) i);
            getFacebookGUI().updateTaskProgress(this);

            // Do some useful work
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }

            // Check the cancel flag
            if (checkForCancel()) {
                return true;
            }
        }

        setTaskState(FacebookTaskState.Finished);
        setMessage("Finished");
        setPercentage(100.0);
        getFacebookGUI().updateTaskProgress(this);
        return true;
    }

    @Override
    public String toString() {
        return "A Sample plugin";
    }
}


```