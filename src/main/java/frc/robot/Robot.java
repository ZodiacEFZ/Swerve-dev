// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.libzodiac.ZDashboard;

public class Robot extends TimedRobot {
    private RobotContainer robotContainer;

    protected Robot() {
        addPeriodic(() -> robotContainer.chassis.update(), 0.05, 0.02);
    }

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
    }

    @Override
    public void disabledInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void autonomousInit() {
        robotContainer.auto.init();
        ZDashboard.selectTab("Auto");
    }

    @Override
    public void teleopInit() {
        ZDashboard.selectTab("Teleoperated");
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousPeriodic() {
        robotContainer.auto.schedule();
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        robotContainer.drive.schedule();
        robotContainer.shoot.schedule();
        CommandScheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledExit() {
    }

    @Override
    public void autonomousExit() {
    }

    @Override
    public void teleopExit() {
    }

    @Override
    public void testExit() {
    }
}
