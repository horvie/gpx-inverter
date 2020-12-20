# gpx-util

Utilitfy for handling gpx files.

Currently only merging of gpx files (with possible reversion) is supported.
Filenames are merged by filename order (ie. 01.gpx, 02.gpx, 03.gpx ...)
If gpx content has to be reversed, it's filename must end with 'r' (ie. 03r.gpx).
