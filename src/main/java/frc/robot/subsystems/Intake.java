package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.libzodiac.Util;
import frc.libzodiac.hardware.Pro775;
import frc.libzodiac.hardware.TalonFXMotor;

public final class Intake extends SubsystemBase {
    private static final double DOWN_POSITION = 3500.0;
    private static final double UP_POSITION = 0.0;
    private static final double STANDBY_POSITION = 0.0;
    private static final double AMP_POSITION = 1200.0;
    public final TalonFXMotor convey = new TalonFXMotor(31);
    public final Pro775 lift = new Pro775(32);
    private final DigitalInput topLimitSwitch = new DigitalInput(8);
    private final DigitalInput bottomLimitSwitch = new DigitalInput(0);

    public Intake() {
        this.lift.with_pid(0.5, 1e-4, 50);
        this.lift.reset();
        this.convey.set_pid(TalonFXMotor.PIDConfig(0, 0, 0, 0));
    }

    public Intake standby() {
        if ((this.lift.get() < STANDBY_POSITION && this.bottomLimitSwitch.get()) || (this.lift.get() > STANDBY_POSITION && this.topLimitSwitch.get())) {
            this.lift.angle(STANDBY_POSITION);
        }
        this.convey.shutdown();
        return this;
    }

    public Intake up() {
        if (this.topLimitSwitch.get()) {
            this.lift.angle(UP_POSITION);
        }
        this.convey.shutdown();
        return this;
    }

    public Intake amp() {
        if ((this.lift.get() < STANDBY_POSITION && this.bottomLimitSwitch.get()) || (this.lift.get() > STANDBY_POSITION && this.topLimitSwitch.get())) {
            this.lift.angle(AMP_POSITION);
        }
        if (Util.approx(this.lift.get(), AMP_POSITION, 150)) {
            this.convey.power(0.4);
        }
        return this;
    }

    public Intake take() {
        this.lift.angle(DOWN_POSITION);
        this.convey.power(-0.4);
        return this;
    }

    public Intake send() {
        if (this.topLimitSwitch.get()) {
            this.lift.angle(UP_POSITION);
        }
        if (!this.topLimitSwitch.get() || this.lift.get() < 100) {
            this.convey.power(0.3);
        }
        return this;
    }

    @Override
    public void periodic() {
    }
}
