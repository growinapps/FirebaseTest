package com.growin.silveryogaapp;

import android.graphics.drawable.Drawable;
import android.net.Uri;

public class ListViewItem {
    private Uri imguri;
    private String titleStr ;

    public ListViewItem()
    {
        //
    }

    public Uri getUri(){return this.imguri;}

    public String getTitle() {
        return this.titleStr ;
    }

    public void setUri(Uri uri) {imguri = uri; }

    public void setTitle(String title) {
        titleStr = title ;
    }

}
