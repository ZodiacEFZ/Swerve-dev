package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.libzodiac.ZCommand;
import frc.libzodiac.ZDashboard;
import frc.libzodiac.Zambda;
import frc.libzodiac.hardware.TalonFXMotor;
import frc.libzodiac.ui.Axis;

public final class Shooter extends SubsystemBase {
    private final Slot0Configs MOTOR_PID = TalonFXMotor.PIDConfig(1, 0, 0, 0);
    private final TalonFXMotor rd = new TalonFXMotor(21);
    private final TalonFXMotor ru = new TalonFXMotor(22);
    private final TalonFXMotor ld = new TalonFXMotor(23);
    private final TalonFXMotor lu = new TalonFXMotor(24);

    public Shooter() {
        
        this.rd.set_pid(MOTOR_PID).set_brake(false);
        this.ru.set_pid(MOTOR_PID).set_brake(false);
        this.ld.set_pid(MOTOR_PID).set_brake(false);
        this.lu.set_pid(MOTOR_PID).set_brake(false);
    }

    public Shooter shoot() {
        return this.shoot(-5000);
    }   

    public Shooter shoot(double speed) {
        ZDashboard.add("shooter", speed);
        this.ld.velocity(-speed); // TODO: check
        this.lu.velocity(speed);
        this.rd.velocity(speed);
        this.ru.velocity(-speed);
        return this;
    }

    public ZCommand ctrl(Axis output) {
        final var speed = output.map(x -> x < 0 ? x : 0).threshold(.05);
        return new Zambda(this, () -> {
            final var v = speed.get();
            if (v == 0) {
                this.standby();
            } else {
                this.shoot(v * 5000);
            }
        });
    }

    public Shooter standby() {
        this.rd.shutdown();
        this.ru.shutdown();
        this.ld.shutdown();
        this.lu.shutdown();
        return this;
    }
}
