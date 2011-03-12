/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010  Catroid development team 
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.tugraz.ist.catroid.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import at.tugraz.ist.catroid.R;
import at.tugraz.ist.catroid.constructionSite.content.ProjectManager;
import at.tugraz.ist.catroid.ui.ProjectActivity;

public class EditSpriteDialog extends Dialog {
    protected ProjectActivity projectActivity;

    public EditSpriteDialog(ProjectActivity projectActivity) {
        super(projectActivity);
        this.projectActivity = projectActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_sprite);
        setTitle(R.string.edit_sprite_dialog_title);

		Button deleteSpriteButton = (Button) findViewById(R.id.deleteSpriteButton);
		deleteSpriteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProjectManager projectManager = ProjectManager.getInstance();
                projectManager.getCurrentProject().getSpriteList().remove(projectActivity.getSpriteToEdit());
                if (projectManager.getCurrentSprite() != null
                        && projectManager.getCurrentSprite().equals(projectActivity.getSpriteToEdit())) {
                    projectManager.setCurrentSprite(null);
                }
                dismiss();
            }
        });
        
		Button renameSpriteButton = (Button) findViewById(R.id.renameSpriteButton);
		renameSpriteButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
				projectActivity.showDialog(ProjectActivity.RENAME_SPRITE_DIALOG);
        		dismiss();
        	}
		});
    }
}