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

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.JEditorPane;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

/**
 * Action opens currently activated nodes in the VIM editor.
 *
 * @author Martin Krauskopf
 */
@ActionID(id = "mk.netbeans.module.vimopen.VimOpenAction", category = "Tools")
@ActionRegistration(displayName = "CTL_OpenWithVimMenuItem")
@ActionReferences(value = {
    @ActionReference(path = "Menu/File"),
    @ActionReference(path = "Editors/text/x-java/Popup")})
public class VimOpenAction extends CookieAction {

    private static final String VIM_ACTION_NAME =
            NbBundle.getMessage(VimOpenAction.class, "CTL_OpenWithVimMenuItem");

    protected void performAction(Node[] activatedNodes) {
        Set<String> absPaths = new LinkedHashSet<String>();
        int lineNumber = -1;
        for (Node node : activatedNodes) {
            Lookup lookup = node.getLookup();
            if (lookup.lookup(EditorCookie.class) == null) {
                continue;
            }
            DataObject obj = lookup.lookup(DataObject.class);
            if (obj == null) {
                continue;
            }
            FileObject fo = obj.getPrimaryFile();
            if (fo != null) {
                File f = FileUtil.toFile(fo);
                if (f == null || !f.exists()) {
                    continue;
                }
                String filePath = f.getAbsolutePath();
                absPaths.add(filePath);
                if (lineNumber == -1) {
                    lineNumber = getLineNumber(node);
                }
            }
        }
        if (absPaths.size() > 0) {
            VimUtil.openWithVim(absPaths, lineNumber);
        }
    }

    protected int mode() {
        return CookieAction.MODE_ANY;
    }

    public String getName() {
        return VIM_ACTION_NAME;
    }

    @Override
    protected String iconResource() {
        return "mk/netbeans/module/vimopen/resources/vim16.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    protected Class<?>[] cookieClasses() {
        return new Class<?>[]{EditorCookie.class};
    }

    private int getLineNumber(Node node) {
        EditorCookie ec = node.getCookie(EditorCookie.class);
        if (ec != null) {
            JEditorPane[] panes = ec.getOpenedPanes();
            if (panes != null && panes.length > 0) {
                return NbEditorUtilities.getLine(panes[0], true).getLineNumber();
            }
        }
        return 0;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

}
