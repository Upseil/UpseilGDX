package com.upseil.gdx.gwt.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Clipboard;

public class SystemAccessClipboard implements Clipboard {
    
    private final String textAreaId;
    private final String activatorId;
    
    public SystemAccessClipboard(String textareaId, String activatorId) {
        this.textAreaId = textareaId;
        this.activatorId = activatorId;
    }

    @Override
    public String getContents() {
        String content = getFromSystemClipboard();
        content = content == null || content.isEmpty() ? null : content;
        Gdx.app.getClipboard().setContents(content);
        return content;
    }
    
    private static native String getFromSystemClipboard() /*-{
        return window.prompt("Paste and confirm with enter");
    }-*/;
    
    @Override
    public void setContents(String content) {
        Gdx.app.getClipboard().setContents(content);
        storeToSystemClipboard(content, textAreaId, activatorId);
    }
    
    private static native void storeToSystemClipboard(String text, String textAreaId, String activatorId) /*-{
        $doc.getElementById(textAreaId).innerHTML = text;
        var activator = $doc.getElementById(activatorId);
        activator.style.visibility = 'visible';
        activator.classList.add('fade-in');
        activator.focus();
    }-*/;
    
}
