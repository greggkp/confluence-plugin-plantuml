/*
 * Copyright (C) 2011 Michael Griffel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * This distribution includes other third-party libraries.
 * These libraries and their corresponding licenses (where different
 * from the GNU General Public License) are enumerated below.
 *
 * PlantUML is a Open-Source tool in Java to draw UML Diagram.
 * The software is developed by Arnaud Roques at
 * http://plantuml.sourceforge.org.
 */
package de.griffel.confluence.plugins.plantuml;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Supported PlantUML Macro parameters.
 */
public class PlantUmlMacroParams {

   public enum Param {
      title, type, border, align, hspace, vspace
   }

   public enum Alignment {
      none(""), left("float: left;"), center("display: block; text-align: center;"), right("float: right;");
      private final String _cssStyle;

      private Alignment(String cssStyle) {
         _cssStyle = cssStyle;
      }

      public String getCssStyle() {
         return _cssStyle;
      }

      public static Alignment getDefault() {
         return none;
      }
   }

   @SuppressWarnings("rawtypes")
   private final Map _params;

   @SuppressWarnings("rawtypes")
   public PlantUmlMacroParams(Map params) {
      _params = params != null ? params : Collections.EMPTY_MAP;
   }

   public String getTitle() {
      return get(Param.title);
   }

   public int getBorder() {
      final String border = get(Param.border);
      return border != null ? Integer.valueOf(border) : 0;
   }

   public int getHspace() {
      final String hspace = get(Param.hspace);
      return hspace != null ? Integer.valueOf(hspace) : 0;
   }

   public int getVspace() {
      final String vspace = get(Param.vspace);
      return vspace != null ? Integer.valueOf(vspace) : 0;
   }

   public String getImageStyle() {
      final StringBuilder sb = new StringBuilder();
      if (getBorder() > 0) {
         sb.append(" border: " + getBorder() + "px solid black;");
      }
      if (getHspace() > 0) {
         sb.append(" hspace=" + getHspace() + "\";");
      }
      if (getVspace() > 0) {
         sb.append(" vspace=" + getVspace() + "\";");
      }
      return sb.toString();
   }

   public Alignment getAlignment() {
      final String align = get(Param.align);
      try {
         return align != null ? Alignment.valueOf(align) : Alignment.getDefault();
      } catch (IllegalArgumentException e) {
         return Alignment.getDefault();
      }
   }

   public DiagramType getDiagramType() {
      final String type = get(Param.type);
      /* final */DiagramType result;
      try {
         result = DiagramType.fromType(type);
      } catch (NoSuchElementException e) {
         result = DiagramType.getDefault();
      }
      return result;
   }

   /*
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return "PlantUmlMacroParams [_params=" + _params + "]";
   }

   private String get(Param param) {
      return (String) _params.get(param.name());
   }
}
