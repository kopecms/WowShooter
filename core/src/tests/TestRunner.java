package tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import wow.shooter.entities.Player;
import wow.shooter.logic.Update;
import wow.shooter.managers.GameManager;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BulletTest.class, EnemyTest.class,
        DataHandlerTest.class, UpdateTest.class, Player.class, GameManager.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}  	