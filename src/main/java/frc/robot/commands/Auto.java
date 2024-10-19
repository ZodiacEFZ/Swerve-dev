package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.libzodiac.ZCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Chassis;

import java.util.LinkedList;
import java.util.Queue;

public class Auto extends ZCommand {
    public static final AutoCommand Default = new AutoCommand() {
        @Override
        public AutoCommand init() {
            commands.clear();
            return this;
        }
    };
    public static final AutoCommand Left = new AutoCommand() {
        @Override
        public AutoCommand init() {
            commands.clear();
            return this;
        }
    };
    public static final AutoCommand Center = new AutoCommand() {
        @Override
        public AutoCommand init() {
            commands.clear();
            return this;
        }
    };
    public static final AutoCommand Right = new AutoCommand() {
        @Override
        public AutoCommand init() {
            commands.clear();
            return this;
        }
    };
    private static final double CHASSIS_POSITION_KP = 0.1;
    private static final double CHASSIS_POSITION_THRESHOLD = 1;
    private static final double CHASSIS_ROTATION_THRESHOLD = 0.05;
    private static final Timer timer = new Timer();
    private static AutoCommand command;
    private static Chassis chassis;
    //ZPath path = new ZPath(""); //todo

    public Auto(RobotContainer robot, AutoCommand cmd) {
        chassis = require(robot.chassis);
        command = cmd;
    }

    public Auto init() {
        command.init();
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

        AutoCommand init();

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
}
