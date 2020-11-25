/**
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Copyright (C) 2020 BUCARE SYSTEM C.A. <http://www.bucaresystems.com> and contributors (see README.md file).
 */

package com.bucaresystems.fasterysync.component;

import com.bucaresystems.fasterysync.base.CustomProcessFactory;
import com.bucaresystems.fasterysync.process.BSCA_CreateFasteryPOSTable;
import com.bucaresystems.fasterysync.process.BSCA_ImportAllSales;

/**
 * Process Factory
 */
public class ProcessFactory extends CustomProcessFactory {

	/**
	 * For initialize class. Register the process to build
	 * 
	 * <pre>
	 * protected void initialize() {
	 * 	registerProcess(PPrintPluginInfo.class);
	 * }
	 * </pre>
	 */
	@Override
	protected void initialize() {
		registerProcess(BSCA_CreateFasteryPOSTable.class);
		registerProcess(BSCA_ImportAllSales.class);
	}

}
