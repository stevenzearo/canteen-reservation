package app.canteen;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author steve
 */
public class CanteenBoApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        load(new CanteenBoModule());
    }
}
