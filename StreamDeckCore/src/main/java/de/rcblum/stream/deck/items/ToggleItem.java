package de.rcblum.stream.deck.items;

import java.awt.image.BufferedImage;

import de.rcblum.stream.deck.event.KeyEvent;
import de.rcblum.stream.deck.util.IconHelper;
import de.rcblum.stream.deck.util.SDImage;

public abstract class ToggleItem extends AbstractStreamItem {
	
	SDImage unmodded = null;
	
	boolean isOn = false;

	public ToggleItem() {
		super(IconHelper.BLACK_ICON);
		this.unmodded = this.rawImg;
	}
 
	public ToggleItem(boolean selected) {
		super(IconHelper.BLACK_ICON);
		this.unmodded = this.rawImg;
		this.isOn = selected;
		this.updateIcon();
	}

	public ToggleItem(SDImage icon, boolean selected) {
		super(icon);
		this.unmodded = icon;
		this.isOn = selected;
		this.updateIcon();
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
		switch (event.getType()) {
		// Trigger onDisplay Method for custom init code
		case ON_DISPLAY:
			this.onDisplay();
			break;
		// Toggle between on and off and call apropriate method.
		case RELEASED_CLICKED:
			this.isOn = !this.isOn;
			updateIcon();
			if (this.isOn) 
				onEnable(true);
			else 
				onDisable(true);
			break;
		default:
			break;
		}
	}

	private void updateIcon() {
		if (this.isOn) {
			BufferedImage selected = IconHelper.getImageFromResource("/resources/icons/selected.png");
			System.out.println("Selected-Frame: " + selected);
			this.rawImg = IconHelper.applyImage(this.unmodded, selected);
		}
		else {
			this.rawImg = this.unmodded;
		}
		this.img = this.text != null ? IconHelper.addText(this.rawImg, this.text, this.textPos) : this.rawImg;
		this.fireIconUpdate(false);
	}
	
	public void setSelected(boolean selected) {
		if (selected != this.isOn) {
			this.isOn = selected;
			updateIcon();
			if(this.isOn)
				onEnable(false);
			else
				onDisable(false);
		}
	}
	
	public boolean isSelected() {
		return isOn;
	}

	/**
	 * Is called when the Item is brought to display
	 */
	protected abstract void onDisplay();
	
	/**
	 * Is called when the Item is toggled to on.
	 */
	protected abstract void onEnable(boolean byEvent);
	
	/**
	 * Is called when the item is toggled to off
	 */
	protected abstract void onDisable(boolean byEvent);

}
