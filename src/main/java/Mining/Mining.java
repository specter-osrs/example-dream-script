package Mining;

import jdk.internal.org.objectweb.asm.commons.Method;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;

public class Mining extends MethodProvider {

    public Timer animationTimer = new Timer(1850);
    public void resetAnimationTimer(){
        animationTimer.reset();
        animationTimer.start();
    }
    String currentRock = "";
    public Area lumbridgeCopperRocks = new Area(3231, 3149, 3226, 3143);
    public Area lumbridgeTinRocks = new Area(3221, 3151, 3227, 3144);

    boolean hasPickaxe(){
        return Inventory.contains(i->i.getName().toLowerCase().contains("pickaxe")) || Equipment.contains(i->i.getName().toLowerCase().contains("pickaxe"));
    }
    public boolean canMine(){
        return animationTimer.finished() && !Inventory.isFull() && hasPickaxe();
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
        setCurrentStage();
        switch(currentStage){
            case BRONZE_BARS:
                runBronzeBars();
            case IRON_POWERMINING:
        }

        //decision tree
       //------------------



        //7485:copper Rocks //Tin 7486:
        //tin rocks object id = 11361

        //----------------

    }


    public void runBronzeBars() {
        currentRock = "Copper";
        if (canMine()) {
            if (GameObjects.closest(i -> i.getName().toLowerCase().contains(currentRock)).exists()) {
                mineTargetRock();
            }
            if (!lumbridgeCopperRocks.contains(Players.getLocal())){
                Walking.walk(lumbridgeCopperRocks);
         }
        }
        if (Inventory.isFull()) {
            bankOres();
        }
    }

    public void mineTargetRock(){
        GameObject targetRock = GameObjects.closest(x -> x.toString().toLowerCase().contains(currentRock) && x.hasAction("Mine"));
        if (!targetRock.getName().toLowerCase().contains(currentRock)) {
            targetRock = GameObjects.closest(x -> x.toString().toLowerCase().contains(currentRock) && x.hasAction("Mine"));
        }
        if(canMine()){
            targetRock.interactForceLeft("Mine");
            sleep(150);
            resetAnimationTimer();
        }
    }

    public void bankOres() {
            if (Inventory.isEmpty()){
                return;
            }
            if (!BankLocation.getNearest().getArea(5).contains(Players.getLocal())) {
                Walking.walk(BankLocation.getNearest().getCenter().getArea(5).getRandomTile());
            }
            if (BankLocation.getNearest().getArea(5).contains(Players.getLocal())) {
                if(Bank.isOpen()){
                    Bank.depositAllExcept(i->i.getName().toLowerCase().contains("pickaxe"));
                    Bank.close();
                }
                if(!Bank.isOpen() && Inventory.contains(i->i.getName().toLowerCase().contains("ore")) || Inventory.contains(i->i.getName().toLowerCase().contains("bar"))|| Inventory.contains(i->i.getName().toLowerCase().contains("uncut"))) {
                    Bank.open();
                    sleep(250);
                    }
                }
            }

    }


