/*******************************************************************************
 * Copyright 2012 See AUTHORS file.
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
 ******************************************************************************/
/*
 * Copyright 2012 Diego Martin Moreno (dmartmorsoft@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
import javax.microedition.midlet.*;
import javax.microedition.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.Canvas;
import java.util.*;
import java.io.*;

/**
 * <p>Title: Mensajes</p>
 * <p>Description: Contiene los metodos para mostrar los mensajes de carga del mapa</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Diego Martín Moreno(c) 2003</p>
 * @author Diego Martín Moreno
 * @version 1.0
 */
public class Mensajes {

  /**
   * Mensaje de loading sin el punto
   * @param g Objeto graphics del mapa
   * @param canvas Pantalla del mapa
   */
  public static void loadingSinPunto(Graphics g, Canvas canvas) {
    g.setStrokeStyle(Graphics.SOLID);
    g.setColor(255, 255, 255);
    g.fillRect( (canvas.getWidth() / 2) - 25, (canvas.getHeight() / 2) - 10, 55,
               20);
    g.setColor(0, 0, 0);
    g.drawRect( (canvas.getWidth() / 2) - 25, (canvas.getHeight() / 2) - 10, 55,
               20);
    g.setFont(
        Font.getFont(
        Font.FACE_PROPORTIONAL,
        Font.STYLE_PLAIN,
        Font.SIZE_SMALL));
    g.drawString(
        "Loading",
        (canvas.getWidth() / 2) - 21,
        (canvas.getHeight() / 2) - 6,
        Graphics.LEFT | Graphics.TOP);
  }

  /**
   * Mensaje de loading con el punto
   * @param g Objeto graphics del mapa
   * @param canvas Pantalla del mapa
   */
  public static void loadingConPunto(Graphics g, Canvas canvas) {
    g.setStrokeStyle(Graphics.SOLID);
    g.setColor(255, 255, 255);
    g.fillRect( (canvas.getWidth() / 2) - 25, (canvas.getHeight() / 2) - 10, 55,
               20);
    g.setColor(0, 0, 0);
    g.drawRect( (canvas.getWidth() / 2) - 25, (canvas.getHeight() / 2) - 10, 55,
               20);
    g.setFont(
        Font.getFont(
        Font.FACE_PROPORTIONAL,
        Font.STYLE_PLAIN,
        Font.SIZE_SMALL));

    g.drawString(
        "Loading .",
        (canvas.getWidth() / 2) - 21,
        (canvas.getHeight() / 2) - 6,
        Graphics.LEFT | Graphics.TOP);
  }

}
