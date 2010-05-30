/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 4481072
 * @summary Basic test for purge method
 */

import java.util.*;

public class Purge {
    public static void main (String[] args) {
        Random rnd = new Random();
        Timer timer = new Timer();
        int cancelled = 0;
        for (long i = 1; i <= 1000; i++) {
            TimerTask task = new TimerTask() {
                public void run() {
                    System.out.println("How come no one ever calls me?");
                }
            };
            timer.schedule(task, i * 60*60*1000); // i hrs. hence.
            if (i != 1 && rnd.nextBoolean()) {
                task.cancel();
                cancelled++;
            }
        }
        int purged = timer.purge();
        if (purged != cancelled)
            throw new RuntimeException(purged + " != " + cancelled);
        purged = timer.purge();
        if (purged != 0)
            throw new RuntimeException(purged + " nonzero");
        timer.cancel();
    }
}
