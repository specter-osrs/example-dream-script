import Mining.Mining;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Logger;

import java.awt.*;

@ScriptManifest(name = "Specter", description = "Specter Automation presents...", author = "Specter",
        version = 1.00, category = Category.MONEYMAKING, image = "")
public class Main extends AbstractScript {

    Mining mining = new Mining();
    //-------ON SCRIPT START-------//
    @Override
    public void onStart() {
        super.onStart();
    }

    //-------MAIN LOOP-------//
    @Override
    public int onLoop() {
        mining.run();

        return 10;

    }

    //-------PAINT LOOP-------//
    @Override
    public void onPaint(Graphics graphics) {
        super.onPaint(graphics);
    }

    //-------ON SCRIPT EXIT-------//
    @Override
    public void onExit() {
        super.onExit();
    }

    //-------ON SCRIPT PAUSED-------//
    @Override
    public void onPause() {
        super.onPause();
    }

    //-------ON SCRIPT RESUMED-------//
    @Override
    public void onResume() {
        super.onResume();
    }

}