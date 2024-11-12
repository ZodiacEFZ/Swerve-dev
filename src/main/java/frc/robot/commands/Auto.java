package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.libzodiac.ZCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

import java.util.LinkedList;
import java.util.Queue;

public class Auto extends ZCommand {
    public static final AutoCommand Left = new AutoCommand() {
        @Override
        public AutoCommand set() {
            return this;
        }
    };
    public static final AutoCommand Center = new AutoCommand() {
        @Override
        public AutoCommand set() {
            return this;
        }
    };
    public static final AutoCommand Right = new AutoCommand() {
        @Override
        public AutoCommand set() {
            return this;
        }
    };
    private static final double CHASSIS_POSITION_KP = 0.1;
    private static final double CHASSIS_POSITION_THRESHOLD = 1;
    private static final double CHASSIS_ROTATION_THRESHOLD = 0.05;
    private static final Timer timer = new Timer();
    private static AutoCommand command;
    //    private static ZPath path = new ZPath(""); // TODO: add path
    private static Chassis chassis;
    private static Intake intake;
    private static Shooter shooter;

    public static final AutoCommand Default = new AutoCommand() {
        @Override
        public AutoCommand set() {
            this.add(() -> Utils.shoot(5));
            return this;
        }
    };

    public Auto(RobotContainer robot, AutoCommand cmd) {
        chassis = require(robot.chassis);
        command = cmd;
    }

    public Auto init() {
        command.init().set();
        timer.start();
        return this;
    }

    @Override
    protected ZCommand exec() {
        command.run();
        return this;
    }

    public interface AutoCommand {
        Queue<AutoLambda> commands = new LinkedList<>();

        default AutoCommand init() {
            commands.clear();

            return this;
        }

        AutoCommand set();

        default AutoCommand add(AutoLambda lambda) {
            commands.add(lambda);
            return this;
        }

        default AutoCommand run() {
            if (!commands.isEmpty()) {
                if (commands.peek().run()) {
                    commands.poll();
                    timer.reset();
                }
            }
            return this;
        }
    }

    public interface AutoLambda {
        boolean run();
    }

    public static class Utils {
        private static final double SHOOTER_PRESTART_TIME = 1;

        public static boolean shoot(double time) {
            if (timer.get() < time) {
                if (timer.get() > SHOOTER_PRESTART_TIME) {
                    intake.send();
                }
                shooter.shoot();
                return false;
            }
            intake.up();
            shooter.standby();
            return true;
        }

        public static boolean intake(double time) {
            if (timer.get() < time) {
                intake.take();
                return false;
            }
            intake.standby();
            return true;
        }
    }
}
