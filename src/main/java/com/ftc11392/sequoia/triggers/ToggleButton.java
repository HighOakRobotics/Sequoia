package com.ftc11392.sequoia.triggers;

import java.util.function.BooleanSupplier;

public class ToggleButton extends ToggleTrigger {
	public ToggleButton(BooleanSupplier isActive, boolean rising) {
		super(isActive, rising);
	}

	public ToggleButton(BooleanSupplier isActive) {
		super(isActive);
	}
}
