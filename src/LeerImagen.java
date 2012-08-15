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
import javax.microedition.*;
import javax.microedition.io.*;
import java.io.*;
import java.util.Date;

/**
 * <p>Title: LeerImagen </p>
 * <p>Description: Clase que lanza un hilo para leer una imagen del servidor vectorial de mapas</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Diego Martín Moreno(c) 2003</p>
 * @author Diego Martín Moreno
 * @version 1.0
 */
public class LeerImagen
    implements Runnable {

  //Hilo de la clase runnable
  Thread hiloLeer;

  //Control terminado carga de la imagen
  private boolean terminado = false;

  //Imagen que devuelve
  private Image mapa;

  //Tamano de la imagen en base a la pantalla del movil
  private String tamaño;

  String url;

  private int posY;

  private int posX;

  private String error = null;

  boolean errorLectura = false;

  /**
   * Devuelve el error de la lectura del mapa
   * @return Descripcion del error
   */
  public String getError() {
    return error;
  }

  /**
   * Control para la lectura del mapa
   * @return booleano, true si es error
   */
  public boolean isError() {
    return errorLectura;
  }

  /**
   * Contructor con lectura del mapa
   * @param anchoV Ancho de la pantalla
   * @param altoV Alto de la pantalla
   * @param X Posición X
   * @param Y Posición Y
   */
  public LeerImagen(int anchoV, int altoV, int X, int Y) {

    posX = X;

    posY = Y;

    tamaño = "&mapsize=" + anchoV + "%20" + altoV;

    try {
      hiloLeer = new Thread(this);
    }
    catch (Exception ex) {
      Propiedades.setConsola("Error:" + ex);
    }
    hiloLeer.start();

  }

  /**
   * Control para cuando ha terminado de leer el mapa
   * @return booleano true cuando tiene la imagen leida
   */
  public boolean imagenLeida() {
    return terminado;
  }

  /**
   * Devuelve la imagen leiada
   * @return imagen del mapa que se ha pedido
   */
  public Image getMapa() {
    return mapa;
  }

  /**
   * Fuerza la limpieza del mapa y para el hilo
   */
  public void cleanMapa() {
    mapa = null;
    terminado = false;
  }

  /**
   * Hilo para leer la imagen
   */
  public void run() {
    try {
      getImage();

    }
    catch (Exception ex) {
      //Consola movil
      Propiedades.setConsola("Error_3:" + ex);
      errorLectura = true;
      error = "" + ex;
    }
  }

  /*--------------------------------------------------
   * Open an http connection and download a png file
   * into a byte array.
   *-------------------------------------------------*/
  private void getImage() throws IOException {

    url =
        Propiedades.getServidor()
        + "?map="
        + Propiedades.getFicheroMap()
        + "&imgext="
        + Propiedades.getImgExt()
        + "&zoom="
        + Propiedades.getZoom()
        + "&img.x="
        + posX
        + "&img.y="
        + posY
        + "&mode=map"
        + Propiedades.getLayer()
        + tamaño;

    //Propiedades.setConsola("url:"+url);
    System.out.println("url:" + url);
    Date inicio = new Date();

    ContentConnection connection = (ContentConnection) Connector.open(url);
    DataInputStream iStrm = connection.openDataInputStream();

    try {
      // ContentConnection includes a length method
      byte imageData[];
      int length = (int) connection.getLength();
      if (length != -1) {
        imageData = new byte[length];

        Propiedades.setContador(length);

        // Read the png into an array
        iStrm.readFully(imageData);
      }
      else { // Length not available...
        ByteArrayOutputStream bStrm = new ByteArrayOutputStream();

        int ch;
        while ( (ch = iStrm.read()) != -1) {
          bStrm.write(ch);

        }
        imageData = bStrm.toByteArray();
        Propiedades.setContador(imageData.length);
        bStrm.close();
      }

      // Create the image from the byte array
      mapa = Image.createImage(imageData, 0, imageData.length);

      Date finaliza = new Date();

      Propiedades.unoMasNumDecargas();
      Propiedades.setTiempoDescargas(finaliza.getTime() - inicio.getTime());

      //Ya ha terminado de cargar
      terminado = true;

    }
    finally {
      // Clean up
      if (iStrm != null) {
        iStrm.close();
      }
      if (connection != null) {
        connection.close();
      }
    }

  }

}
