package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.libzodiac.Zwerve;
import frc.libzodiac.hardware.Pigeon;
import frc.libzodiac.hardware.group.TalonFXSwerve;
import frc.libzodiac.util.Vec2D;

public class Chassis extends Zwerve {
    // TODO: Swerve zero position
    private static final TalonFXSwerve front_left = new TalonFXSwerve(5, 1, 9, 3581).invert(false, true);
    private static final TalonFXSwerve front_right = new TalonFXSwerve(8, 4, 12, 1921).invert(false, false);
    private static final TalonFXSwerve rear_left = new TalonFXSwerve(6, 2, 10, 408).invert(true, true);
    private static final TalonFXSwerve rear_right = new TalonFXSwerve(7, 3, 11, 149).invert(true, true);

    private static final Pigeon gyro = new Pigeon(0);

    private static final Pose2d initialPose = new Pose2d(0, 0, Rotation2d.fromRadians(0)); // TODO: Initial pose
    private static final Vec2D size = new Vec2D(114, 114); // TODO: Robot size

    /**
     * Creates a new Chassis.
     */
    public Chassis() {
        super(front_left, front_right, rear_left, rear_right, gyro, size, initialPose);

        final var v = new PIDController(0.2, 5, 0);
        final var a = new PIDController(0.3, 0, 0);

        front_left.set_pid(v, a);
        front_right.set_pid(v, a);
        rear_left.set_pid(v, a);
        rear_right.set_pid(v, a);
    }
}