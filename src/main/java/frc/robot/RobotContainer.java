// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.libzodiac.Zambda;
import frc.libzodiac.Zamera;
import frc.libzodiac.ui.Axis;
import frc.libzodiac.ui.Xbox;
import frc.robot.commands.Auto;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
    public final Chassis chassis = new Chassis();
    public final Intake intake = new Intake();
    public final Shooter shooter = new Shooter();
    public final Zamera camera = new Zamera();
    public final Xbox driver = new Xbox(0);
    public final Xbox controller = new Xbox(1);
    public final Auto auto = new Auto(this, Auto.Default); // TODO: change command

    /**
     * The command to drive the robot.
     */
    public final Command drive = this.chassis.drive(
            driver.l().switched().inverted().threshold(0.05).map(Axis.ATAN_FILTER),
            driver.r().switched().threshold(0.05),
            driver.lx().inverted().threshold(0.05).map(Axis.ATAN_FILTER),
            driver.ly().inverted().threshold(0.05).map(Axis.ATAN_FILTER),
            driver.rx().inverted().threshold(0.05).map(Axis.ATAN_FILTER),
            driver.ry().inverted().threshold(0.05).map(Axis.ATAN_FILTER),
            driver.rt().as_button(0.2),
            driver.lt().as_button(0.2),
            driver.lb());

    public final Command shoot = this.shooter.ctrl(controller.ry());

    public RobotContainer() {
        this.configureBindings();
        this.init();
    }

    private void configureBindings() {
        this.driver.a().on_press(new Zambda(this.chassis, () -> {
            final var headless = this.chassis.toggle_headless();
            this.driver.rumble(0.3, headless ? 1 : 0.4);
        }));
        this.driver.b().on_press(new Zambda(this.chassis, () -> {
            this.chassis.reset_headless();
            this.driver.rumble(0.3);
        }));

        this.controller.a().on_down(new Zambda(this.intake, this.intake::amp))
                .on_release(new Zambda(this.intake, this.intake::standby));
        this.controller.b().on_down(new Zambda(this.intake, this.intake::up));
        this.controller.lb().on_press(new Zambda(this.intake, this.intake.lift::reset));
        this.controller.lt().as_button().on_down(new Zambda(this.intake, this.intake::take))
                .on_release(new Zambda(this.intake, this.intake::standby));
        this.controller.rt().as_button()
                .on_down(new Zambda(this.intake, this.intake::send))
                .on_release(new Zambda(this.intake, this.intake::standby));
    }

    private RobotContainer init() {
        camera.start();
        return this;
    }
}
