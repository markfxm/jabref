/*  Copyright (C) 2003-2011 JabRef contributors.
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/
package net.sf.jabref;

import net.sf.jabref.gui.AutoCompleteListener;
import net.sf.jabref.util.StringUtil;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class FieldTextField extends JTextField implements FieldEditor {

    private final String fieldName;
    private final JLabel label;
    private UndoManager undo;
    private AutoCompleteListener autoCompleteListener = null;


    //protected UndoManager undo = new UndoManager();

    public FieldTextField(String fieldName_, String content, boolean changeColorOnFocus) {
        super(content);

        // Listen for undo and redo events
        /*getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent evt) {
                undo.addEdit(evt.getEdit());
            }
        });*/
        setupUndoRedo();

        updateFont();

        // Add the global focus listener, so a menu item can see if this field
        // was focused when
        // an action was called.
        addFocusListener(Globals.focusListener);
        if (changeColorOnFocus) {
            addFocusListener(new FieldEditorFocusListener());
        }
        fieldName = fieldName_;
        label = new FieldNameLabel(' ' + StringUtil.nCase(fieldName) + ' ');
        // label = new JLabel(" "+Util.nCase(fieldName)+" ", JLabel.CENTER);
        // label.setBorder(BorderFactory.createEtchedBorder());
        setBackground(GUIGlobals.validFieldBackgroundColor);
        setForeground(GUIGlobals.editorTextColor);

        // label.setOpaque(true);
        // if ((content != null) && (content.length() > 0))
        // label.setForeground(GUIGlobals.entryEditorLabelColor);
        // At construction time, the field can never have an invalid value.
        // else label.setForeground(GUIGlobals.nullFieldColor);

        FieldTextMenu popMenu = new FieldTextMenu(this);
        this.addMouseListener(popMenu);
        label.addMouseListener(popMenu);
    }

    private void setupUndoRedo() {
        undo = new UndoManager();
        Document doc = getDocument();

        // Listen for undo and redo events
        doc.addUndoableEditListener(evt -> undo.addEdit(evt.getEdit()));

        // Create an undo action and add it to the text component
        getActionMap().put("Undo",
                new AbstractAction("Undo") {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (undo.canUndo()) {
                                undo.undo();
                            }
                        } catch (CannotUndoException ignored) {
                        }
                    }
                });

        // Bind the undo action to ctl-Z
        getInputMap().put(Globals.prefs.getKey("Undo"), "Undo");

        // Create a redo action and add it to the text component
        getActionMap().put("Redo",
                new AbstractAction("Redo") {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (undo.canRedo()) {
                                undo.redo();
                            }
                        } catch (CannotRedoException ignored) {
                        }
                    }
                });

        // Bind the redo action to ctl-Y
        getInputMap().put(Globals.prefs.getKey("Redo"), "Redo");
    }

    @Override
    public void setText(String t) {
        super.setText(t);
        if (undo != null) {
            undo.discardAllEdits();
        }
    }

    @Override
    public void append(String text) {
        setText(getText() + text);
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public JLabel getLabel() {
        return label;
    }

    @Override
    public void setLabelColor(Color c) {
        label.setForeground(c);
        throw new NullPointerException("ok");
    }

    @Override
    public JComponent getPane() {
        return this;
    }

    @Override
    public JComponent getTextComponent() {
        return this;

    }

    @Override
    public void setActiveBackgroundColor() {
        setBackground(GUIGlobals.activeBackground);
    }

    @Override
    public void setValidBackgroundColor() {
        setBackground(GUIGlobals.validFieldBackgroundColor);
    }

    @Override
    public void setInvalidBackgroundColor() {
        setBackground(GUIGlobals.invalidFieldBackgroundColor);
    }

    @Override
    public void updateFontColor() {
        setForeground(GUIGlobals.editorTextColor);
    }

    @Override
    public void updateFont() {
        setFont(GUIGlobals.CURRENTFONT);
    }

    /*public void paint(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
    	if (antialias)
    		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	super.paint(g2);
    }*/

    @Override
    public void paste(String textToInsert) {
        int sel = getSelectionEnd() - getSelectionStart();
        if (sel < 1) {
            int cPos = getCaretPosition();
            select(cPos, cPos);
        }
        replaceSelection(textToInsert);
    }

    @Override
    public boolean hasUndoInformation() {
        return false;//undo.canUndo();
    }

    @Override
    public void undo() {
        /*try {
            if (undo.canUndo()) {
                undo.undo();
            }
        } catch (CannotUndoException e) {
        }*/
    }

    @Override
    public boolean hasRedoInformation() {
        return false;//undo.canRedo();
    }

    @Override
    public void redo() {
        /*try {
            if (undo.canRedo()) {
                undo.redo();
            }
        } catch (CannotUndoException e) {
        }*/

    }

    @Override
    public void addUndoableEditListener(UndoableEditListener listener) {
        getDocument().addUndoableEditListener(listener);
    }

    @Override
    public void setAutoCompleteListener(AutoCompleteListener listener) {
        autoCompleteListener = listener;
    }

    @Override
    public void clearAutoCompleteSuggestion() {
        if (autoCompleteListener != null) {
            autoCompleteListener.clearCurrentSuggestion(this);
        }
    }
}
