package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.libzodiac.ZCommand;
import frc.libzodiac.util.Vec2D;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Chassis;

import java.util.LinkedList;
import java.util.Queue;

public class Auto extends ZCommand {
    public static final AutoCommand Fallback = new AutoCommand() {
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

    public Auto(RobotContainer robot) {
        chassis = require(robot.chassis);
    }

    private static boolean go_pos(Vec2D pos, double yaw) {
        final var deltaPos = pos.sub(Chassis.inav.getPosition());
        final var deltaYaw = yaw - Chassis.inav.getYaw();
        chassis.go(deltaPos.mul(CHASSIS_POSITION_KP), yaw);
        return deltaPos.r() < CHASSIS_POSITION_THRESHOLD && Math.abs(deltaYaw) < CHASSIS_ROTATION_THRESHOLD;
    }

    private static boolean go(Vec2D vel, double yaw, double time) {
        if (timer.get() < time) {
            chassis.go_yaw(vel, yaw);
            return false;
        }
        chassis.go(new Vec2D(0, 0), 0);
        return true;
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
