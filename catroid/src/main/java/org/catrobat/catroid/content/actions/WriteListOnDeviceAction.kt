/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2021 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.content.actions

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.catrobat.catroid.ProjectManager
import org.catrobat.catroid.formulaeditor.UserList
import org.catrobat.catroid.io.DeviceListAccessor
import org.catrobat.catroid.io.DeviceUserDataAccessor
import org.catrobat.catroid.utils.CoroutineAsyncTask
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class WriteListOnDeviceAction : AsynchronousAction() {
    var userList: UserList? = null
    private var writeActionFinished = false
    val projectDirectory = ProjectManager.getInstance().currentProject.directory
    private val scopeIO = CoroutineScope(Dispatchers.IO)

    override fun act(delta: Float): Boolean {
        return if (userList == null) {
            true
        } else super.act(delta)
    }

    override fun initialize() {
        writeActionFinished = false
        executeWrite(userList)
    }

    private fun executeWrite(list: UserList?) {
        scopeIO.launch {
            DeviceListAccessor(projectDirectory).writeUserData(list)
            writeActionFinished = true
        }
    }

    override fun isFinished(): Boolean = writeActionFinished
}
