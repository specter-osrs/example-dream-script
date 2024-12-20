package Mining;

import jdk.internal.org.objectweb.asm.commons.Method;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;

public class Mining extends MethodProvider {
    String currentRock = "";

    public Timer animationTimer = new Timer(1850);

    public void resetAnimationTimer(){
        animationTimer.reset();
        animationTimer.start();
    }

    public Stage currentStage;
    public void setCurrentStage(){
        if(currentStage != null){
            currentStage = Stage.BRONZE_BARS;
        }
        if(Skills.getRealLevel(Skill.MINING) < 41){
            currentStage=Stage.BRONZE_BARS;
        }
        if(Skills.getRealLevel(Skill.MINING) >= 41){
            currentStage=Stage.IRON_POWERMINING;
        }
    }

    public void run() {
        switch(currentStage){
            case BRONZE_BARS:
            case IRON_POWERMINING:
        }

        //decision tree
       //------------------



        //7485:copper Rocks //Tin 7486:
        //tin rocks object id = 11361

        //----------------

    }

    boolean hasPickaxe(){
        return Inventory.contains(i->i.getName().toLowerCase().contains("pickaxe")) || Equipment.contains(i->i.getName().toLowerCase().contains("pickaxe"));
    }

    public boolean canMine(){
        return animationTimer.finished() && !Inventory.isFull() && hasPickaxe();
    }

    public void mineTargetRock(){
        GameObject targetRock = GameObjects.closest(x -> x.toString().toLowerCase().contains(currentRock) && x.hasAction("Mine"));
        if (!targetRock.getName().toLowerCase().contains(currentRock)) {
            targetRock = GameObjects.closest(x -> x.toString().toLowerCase().contains(currentRock) && x.hasAction("Mine"));
        }
        if(canMine()){
            targetRock.interactForceLeft("Mine");
            resetAnimationTimer();
        }
    }

    //need 60 mining. we can get to 41 mining via tin/copper, then powermine iron to 60 with a rune pickaxe, sell smelted bars for it


}
