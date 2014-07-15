/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.tests.components.grid.basicfeatures;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.vaadin.testbench.TestBenchElement;

public class GridHeaderTest extends GridStaticSectionTest {

    @Test
    public void testHeaderVisibility() throws Exception {
        openTestURL();

        // Column headers should be visible by default
        assertEquals(GridBasicFeatures.COLUMNS, getGridHeaderRowCells().size());
    }

    @Test
    public void testHeaderCaptions() throws Exception {
        openTestURL();

        assertHeaderTexts(0, 0);
    }

    @Test
    public void testHeadersWithInvisibleColumns() throws Exception {
        openTestURL();

        selectMenuPath("Component", "Columns", "Column 1", "Visible");
        selectMenuPath("Component", "Columns", "Column 3", "Visible");

        List<TestBenchElement> cells = getGridHeaderRowCells();
        assertEquals(GridBasicFeatures.COLUMNS - 2, cells.size());

        assertText("Header (0,0)", cells.get(0));
        assertText("Header (0,2)", cells.get(1));
        assertText("Header (0,4)", cells.get(2));

        selectMenuPath("Component", "Columns", "Column 3", "Visible");

        cells = getGridHeaderRowCells();
        assertEquals(GridBasicFeatures.COLUMNS - 1, cells.size());

        assertText("Header (0,0)", cells.get(0));
        assertText("Header (0,2)", cells.get(1));
        assertText("Header (0,3)", cells.get(2));
        assertText("Header (0,4)", cells.get(3));
    }

    @Test
    public void testAddRows() throws Exception {
        openTestURL();

        selectMenuPath("Component", "Header", "Append row");

        assertHeaderCount(2);
        assertHeaderTexts(0, 0);
        assertHeaderTexts(1, 1);

        selectMenuPath("Component", "Header", "Prepend row");

        assertHeaderCount(3);
        assertHeaderTexts(2, 0);
        assertHeaderTexts(0, 1);
        assertHeaderTexts(1, 2);

        selectMenuPath("Component", "Header", "Append row");

        assertHeaderCount(4);
        assertHeaderTexts(2, 0);
        assertHeaderTexts(0, 1);
        assertHeaderTexts(1, 2);
        assertHeaderTexts(3, 3);
    }

    @Test
    public void testRemoveRows() throws Exception {
        openTestURL();

        selectMenuPath("Component", "Header", "Prepend row");
        selectMenuPath("Component", "Header", "Append row");

        selectMenuPath("Component", "Header", "Remove top row");

        assertHeaderCount(2);
        assertHeaderTexts(0, 0);
        assertHeaderTexts(2, 1);

        selectMenuPath("Component", "Header", "Remove bottom row");
        assertHeaderCount(1);
        assertHeaderTexts(0, 0);
    }

    private void assertHeaderCount(int count) {
        assertEquals("header count", count, getGridElement().getHeaderCount());
    }
}
