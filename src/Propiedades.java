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
import javax.microedition.lcdui.*;

/**
 *
 * <p>Title: Propiedades </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Diego Martín Moreno(c) 2003</p>
 * @author Diego Martín Moreno
 * @version 1.0
 */
public class Propiedades {

	public final static int CENTRAL = 0;
	public final static int ARRIBA = 1;
	public final static int ABAJO = 2;
	public final static int DERECHA = 3;
	public final static int IZQUIERDA = 4;

	public static int numDescargas;
	public static long tiempoDescargas;
	public static String layer;
	public static String servidor;
	public static String ficheroMap;
	public static String imgext;
	public static int origenX;
    public static int origenY;
    public static int factor;
	public static String zoom;
	public static long contadorBytes;
	public static StringBuffer entradaConsola = new StringBuffer();

	/**
	 *
	 * Inicializa la clase con las propiedades
	 * se lee desde el fichero jad
 	 *
	 */
	public Propiedades(MIDlet midlet) {

		servidor = midlet.getAppProperty("servidor");
		ficheroMap = midlet.getAppProperty("ficheroMap");
		origenX = Integer.parseInt(midlet.getAppProperty("origenX"));
        origenY = Integer.parseInt(midlet.getAppProperty("origenY"));
        factor = Integer.parseInt(midlet.getAppProperty("factor"));
		layer = midlet.getAppProperty("layer");
		zoom = midlet.getAppProperty("zoom");


	}

        public static int getFactor() {
          return factor;
        }

	public static void setImgExt(int X, int Y) {
		imgext = (origenX-X)+".000000+"+(origenY-Y)+".000000+"+(origenX+X)+".000000+"+(origenY+Y)+".000000";

	}


	public static void setTiempoDescargas(long cont) {
		tiempoDescargas += cont;
	}

	public static void unoMasNumDecargas() {
		numDescargas += 1;
	}

	public static int getNumDescargas() {
		return numDescargas;
	}

	public static long getTiempoDescarga() {
		return tiempoDescargas;
	}

	public static String getImgExt() {

		return imgext;

	}

	public static String getZoom() {

		return zoom;

	}

	public static String getLayer() {

		return layer;

	}

	public static String getFicheroMap() {
		return ficheroMap;
	}

	public static String getServidor() {
		return servidor;
	}

	public static void setContador(long cont) {
		contadorBytes += cont;
	}

	public static long getContador() {
		return contadorBytes / 1024;
	}

	public static void setConsola (String texto){
		entradaConsola.append(">"+texto+"\n");			
	}

	public static String getConsola (){
		if (entradaConsola.length() > 5000) {
			return "Tamaño consola excedido";
		} else {
			return entradaConsola.toString();
		}
	}
}
