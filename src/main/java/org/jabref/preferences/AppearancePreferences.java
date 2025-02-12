package org.jabref.preferences;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.jabref.gui.theme.Theme;

public class AppearancePreferences {
    private final BooleanProperty shouldOverrideDefaultFontSize;
    private final IntegerProperty mainFontSize;
    private final IntegerProperty defaultFontSize;
    private final ObjectProperty<Theme> theme;
    private final BooleanProperty automaticThemeDetection;

    public AppearancePreferences(boolean shouldOverrideDefaultFontSize, int mainFontSize, int defaultFontSize, Theme theme,
                                 boolean automaticThemeDetection) {
        this.shouldOverrideDefaultFontSize = new SimpleBooleanProperty(shouldOverrideDefaultFontSize);
        this.mainFontSize = new SimpleIntegerProperty(mainFontSize);
        this.defaultFontSize = new SimpleIntegerProperty(defaultFontSize);
        this.theme = new SimpleObjectProperty<>(theme);
        this.automaticThemeDetection = new SimpleBooleanProperty(automaticThemeDetection);

    }
    public boolean automaticThemeDetection() {
        return automaticThemeDetection.get();
    }

    public void setautomaticThemeDetection(boolean newValue) {
        automaticThemeDetection.set(newValue);
    }

    public BooleanProperty automaticThemeDetectionProperty() {
        return automaticThemeDetection;
    }

    public boolean shouldOverrideDefaultFontSize() {
        return shouldOverrideDefaultFontSize.get();
    }

    public void setShouldOverrideDefaultFontSize(boolean newValue) {
        shouldOverrideDefaultFontSize.set(newValue);
    }

    public BooleanProperty shouldOverrideDefaultFontSizeProperty() {
        return shouldOverrideDefaultFontSize;
    }

    public int getMainFontSize() {
        return mainFontSize.get();
    }

    public int getDefaultFontSize() {
        return defaultFontSize.get();
    }

    public void setMainFontSize(int mainFontSize) {
        this.mainFontSize.set(mainFontSize);
    }

    public IntegerProperty mainFontSizeProperty() {
        return mainFontSize;
    }

    public Theme getTheme() {
        return theme.get();
    }

    public void setTheme(Theme theme) {
        this.theme.set(theme);
    }

    public ObjectProperty<Theme> themeProperty() {
        return theme;
    }
}
