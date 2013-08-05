Introduction
============
Simple modules which allows to open currently selected node(s), for example
files in NetBeans project explorer or documents opened in the NB editor, in the
Vim editor on the same position as it is currently set in NetBeans editor.

The action reuses the same Vim instance when invoked multiple times.

Installation
------------
1. clone [repository](http://github.com/mkrauskopf/nb-vim-open)
2. open `nb-vim-open` module in NetBeans
3. right-click `Vim Open` node in project explorer and select `Create NBM`.


Usage
-----
Select some node(s) (files in the project explorer) or just focus NetBeans
editor and invoke `File` -> `Open in Vim` from NetBeans menu when (or just press
`Alt-F`, `Enter`).

The action is available from other several places like editor's popup menu.

