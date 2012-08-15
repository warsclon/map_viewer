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
 *
 * <p>Title: MoverPantalla </p>
 * <p>Description: Desplaza las imagenes del mapa en la pantalla</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Diego Martín Moreno(c) 2003</p>
 * @author Diego Martín Moreno
 * @version 1.0
 */
public class MoverPantalla {

  /**
   * Mueve verticalmente el mapa
   * @param g Objeto graphics de la pantalla
   * @param mapas Array de imagenes del mapa
   * @param canvas Pantalla para pintar
   * @param desplazamientoY Desplazamiento de las imagenes vertical
   * @param desplazamientoX Desplazamiento de las imagenes horizontal
   */
  public static void movimientoVertical(Graphics g, Image[] mapas,
                                        Canvas canvas, int desplazamientoX,
                                        int desplazamientoY) {
    g.drawImage(
        mapas[Propiedades.ARRIBA],
        desplazamientoX,
        desplazamientoY - canvas.getHeight(),
        Graphics.LEFT | Graphics.TOP);
    g.drawImage(
        mapas[Propiedades.CENTRAL],
        desplazamientoX,
        desplazamientoY,
        Graphics.LEFT | Graphics.TOP);
    g.drawImage(
        mapas[Propiedades.ABAJO],
        desplazamientoX,
        desplazamientoY + canvas.getHeight(),
        Graphics.LEFT | Graphics.TOP);
  }

  /**
   * Mueve horizontalmente el mapa
   * @param g Objeto graphics de la pantalla
   * @param mapas Array de imagenes del mapa
   * @param canvas Pantalla para pintar
   * @param desplazamientoY Desplazamiento de las imagenes vertical
   * @param desplazamientoX Desplazamiento de las imagenes horizontal
   */
  public static void movimientoHorizontal(Graphics g, Image[] mapas,
                                          Canvas canvas, int desplazamientoX,
                                          int desplazamientoY) {
    g.drawImage(
        mapas[Propiedades.IZQUIERDA],
        desplazamientoX - canvas.getWidth(),
        desplazamientoY,
        Graphics.LEFT | Graphics.TOP);
    g.drawImage(
        mapas[Propiedades.CENTRAL],
        desplazamientoX,
        desplazamientoY,
        Graphics.LEFT | Graphics.TOP);
    g.drawImage(
        mapas[Propiedades.DERECHA],
        desplazamientoX + canvas.getWidth(),
        desplazamientoY,
        Graphics.LEFT | Graphics.TOP);

  }

  public static void inicio (Graphics g, Image[] mapas) {
    g.drawImage(
    mapas[Propiedades.CENTRAL],
    0,
    0,
    Graphics.LEFT | Graphics.TOP);
  }

}