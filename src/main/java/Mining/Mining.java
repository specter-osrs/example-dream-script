package Mining;

import org.dreambot.api.methods.Calculations;
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

import java.util.Objects;


public class Mining extends MethodProvider {

    public Timer animationTimer = new Timer(Calculations.random(850,1300));
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
        if(Skills.getRealLevel(Skill.MINING) < 99){
            currentStage=Stage.BRONZE_BARS;
        }
        if(Skills.getRealLevel(Skill.MINING) >= 99){
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
        if (Players.getLocal().isMoving()) sleep(Calculations.random(150,500));
        if (Inventory.isFull()) {
            bankOres();
        }
        if(Players.getLocal().isAnimating()) resetAnimationTimer();

        if(Inventory.isEmpty()) currentRock = "Copper";
        if(Inventory.count(436) >= 14) currentRock = "Tin";
        if(Inventory.count(436) < 14) currentRock = "Copper";

        //----COPPER-------//

        if (!Inventory.isFull() && canMine() && currentRock.contains("Copper")) {
            if (!lumbridgeCopperRocks.contains(Players.getLocal())){
                Walking.walk(lumbridgeCopperRocks);
         }
            if(lumbridgeCopperRocks.contains(Players.getLocal())){
                sleep(Calculations.random(150,500));
                mineTargetRock();
            }
        }
        if (!Inventory.isFull() &&  canMine() && currentRock.contains("Tin")) {
            if (!lumbridgeTinRocks.contains(Players.getLocal())){
                Walking.walk(lumbridgeTinRocks);
            }
            if(lumbridgeTinRocks.contains(Players.getLocal())){
                mineTargetRock();
            }
        }

        //------TIN-------//

        }



    public void mineTargetRock(){
        if(animationTimer.remaining()!=0) {
            sleep(150);
        }
        if(!canMine()){
            sleep(Calculations.random(150,1250));
            return;
        }
        GameObject targetRock = GameObjects.closest(x -> x.toString().toLowerCase().contains(currentRock.toLowerCase()) && x.hasAction("Mine"));
        if (!targetRock.getName().toLowerCase().contains(currentRock.toLowerCase())) {
            targetRock = GameObjects.closest(x -> x.toString().toLowerCase().contains(currentRock.toLowerCase()) && x.hasAction("Mine"));
        }
        if(canMine() && targetRock!=null){
            targetRock.interactForceLeft("Mine");
            sleep(500);
            resetAnimationTimer();
             targetRock = GameObjects.closest(x -> x.toString().toLowerCase().contains(currentRock.toLowerCase()) && x.hasAction("Mine"));
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
                    sleep(Calculations.random(150,500));
                    Bank.depositAllExcept(i->i.getName().toLowerCase().contains("pickaxe"));
                    sleep(Calculations.random(150,500));
                    Bank.close();
                    sleep(Calculations.random(350,1250));
                }
                if(!Bank.isOpen() && Inventory.contains(i->i.getName().toLowerCase().contains("ore")) || Inventory.contains(i->i.getName().toLowerCase().contains("bar"))|| Inventory.contains(i->i.getName().toLowerCase().contains("uncut"))) {
                    Bank.open();
                    sleep(Calculations.random(350,1250));
                    }
                }
            }

            public Timer microSleepTimer = new Timer(Calculations.random(60000,180000));


    }


