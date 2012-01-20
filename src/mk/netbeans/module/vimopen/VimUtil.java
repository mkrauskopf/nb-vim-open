/*
 * Copyright 2012, Martin Krauskopf
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mk.netbeans.module.vimopen;

import java.io.IOException;
import java.util.Set;
import org.openide.awt.StatusDisplayer;

/**
 * @author Martin Krauskopf
 */
final class VimUtil {

    /** Utility methods only. No instances supposed. */
    private VimUtil() {
    }

    /** Open given files in VIM. */
    static boolean openWithVim(Set<String> absPaths, int lineNumber) {
        // XXX: pass also column number
        StringBuilder cmd = new StringBuilder("gvim"
                + " --servername NB_VIM_SERVER"
                + " --remote-silent +" + (lineNumber + 1));
//                " \"+call cursor(" + lineNumber + ",0)\"");
        for (String path : absPaths) {
            cmd.append(" ").append(path);
        }
        try {
            Runtime.getRuntime().exec(cmd.toString());
            StatusDisplayer.getDefault().setStatusText("Opening file(s) in Vim...");
            return true;
        } catch (IOException e) {
            StatusDisplayer.getDefault().setStatusText("Cannot open "
                    + cmd.toString() + " in VIM. Probably 'gvim' is not on the system"
                    + "PATH (" + e.getMessage() + ")");
            return false;
        }
    }

}
