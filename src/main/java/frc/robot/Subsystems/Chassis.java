package frc.robot.Subsystems;

import frc.libzodiac.ZInertialNavigation;
import frc.libzodiac.Zwerve;
import frc.libzodiac.hardware.Pigeon;
import frc.libzodiac.hardware.group.TalonFXSwerve;
import frc.libzodiac.util.PIDProfile;
import frc.libzodiac.util.Vec2D;

public class Chassis extends Zwerve {
    // TODO: Swerve zero position
    private static final TalonFXSwerve front_left = new TalonFXSwerve(5, 1, 9, 3581);
    private static final TalonFXSwerve front_right = new TalonFXSwerve(8, 4, 12, 1921);
    private static final TalonFXSwerve rear_left = new TalonFXSwerve(6, 2, 10, 408);
    private static final TalonFXSwerve rear_right = new TalonFXSwerve(7, 3, 11, 149);

    private static final Pigeon gyro = new Pigeon(0);

    public static final ZInertialNavigation inav = new ZInertialNavigation(gyro);

    /**
     * Creates a new Chassis.
     */
    public Chassis() {
        super(front_left, front_right, rear_left, rear_right, gyro.yaw(), new Vec2D(114, 114)); // TODO: Robot size

        final var v = new PIDProfile(0.2, 5, 0);
        final var a = new PIDProfile(0.3, 0, 0);

        front_left.speed_motor.set_pid(v);
        front_left.angle_motor.set_pid(a);
        front_right.speed_motor.set_pid(v);
        front_right.angle_motor.set_pid(a);
        rear_left.speed_motor.set_pid(v);
        rear_left.angle_motor.set_pid(a);
        rear_right.speed_motor.set_pid(v);
        rear_right.angle_motor.set_pid(a);
    }
}