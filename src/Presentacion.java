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
 * <p>Title: Presentacion </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Diego Martín Moreno(c) 2003</p>
 * @author Diego Martín Moreno
 * @version 1.0
 */
public class Presentacion
    extends Canvas
    implements Runnable {

  //Objeto midlet
  private MIDlet midlet;

  //Imagen de la presentación
  private Image fondo;

  //Tiempo de espera de la animación
  private int sleepTh = 200;

  //Hilo de la clase runnable
  private Thread hiloAni;

  //Hilo encargado de leer las imagenes
  private LeerImagen leerImagen;

  //Contador de imagenes leidas
  private int imagenesLeidas;

  //Array con todas las imagenes cargadas
  private Image[] mapasArray;

  //Control para cuando termina de cargar las 5 imagenes
  private boolean termino;

  //Control para mantener hilo en marcha
  private boolean hiloArrancado = true;

  //control sacar o no los puntos en la presentacion
  private boolean punto;

  //Puntos a sacar
  private String puntos = ".";

  //Texto de error
  static String textError = null;

  /**
   * Constructor de la presentación y se le pasa
   * el objeto midlet
   * @param mlet Objeto MIDlet
   */
  public Presentacion(MIDlet mlet) {

    midlet = mlet;

    try {

      //Inicializa extension con el tamaño de la pantalla
      Propiedades.setImgExt(getWidth() / 2, getHeight() / 2);

      //Logo del inicio
      fondo = Image.createImage("/inicio.png");

      //Crea array con los 5 mapas del inicio
      mapasArray = new Image[5];

      //Arranca hilo de la presentacion
      hiloAni = new Thread(this);

      //carga primera imagen la central x=0 y=0
      leerImagen =
          new LeerImagen(
          getWidth(),
          getHeight(),
          0,
          0);

    }
    catch (Exception ex) {
      Propiedades.setConsola("Error_1");
    }
    //Inicia la presentacion
    hiloAni.start();
  }

  /**
   * Pinta la pantalla de la presentación
   * @param g Objeto graphics
   */
  public void paint(Graphics g) {

    //color blanco
    g.setColor(255, 255, 255);

    //pinta fondo blanco
    g.fillRect(0, 0, getWidth(), getHeight());

    //Pinta logo
    g.drawImage(fondo,
                (getWidth() / 2) - 45,
                (getHeight() / 2) - 15,
                Graphics.LEFT | Graphics.TOP);

    //color negro
    g.setColor(0, 0, 0);

    if (!leerImagen.isError()) {
      if (termino) { //Termino de leer todas las imagenes
        g.drawString(
            "Press key",
            (getWidth() / 2) - 25,
            (getHeight() / 2) + 25,
            Graphics.LEFT | Graphics.TOP);

      }
      else {
        if (punto) {
          g.drawString(
              "Loading " + puntos,
              (getWidth() / 2) - 25,
              (getHeight() / 2) + 25,
              Graphics.LEFT | Graphics.TOP);
        }
        else {
          g.drawString(
              "Loading ",
              (getWidth() / 2) - 25,
              (getHeight() / 2) + 25,
              Graphics.LEFT | Graphics.TOP);
        }
      }
    }
    else {
      g.drawString(
          textError,
          0,
          (getHeight() / 2) + 25,
          Graphics.LEFT | Graphics.TOP);

    }
  }

  /**
   * Método run que mueve la presentación
   */
  public void run() {
    while (hiloArrancado) {
      try {
        hiloAni.sleep(sleepTh);

        //control punto que sale
        if (punto) {
          punto = false;
        }
        else {
          punto = true;

        }
        if (leerImagen.imagenLeida()) {

          //copiar imagen
          mapasArray[imagenesLeidas] = leerImagen.getMapa();

          imagenesLeidas++;

          if (imagenesLeidas < 5) {

            //cargar siguiente imagen
            switch (imagenesLeidas) {
              case Propiedades.ARRIBA: //Leermos la superior
                leerImagen =
                    new LeerImagen(getWidth(), getHeight(), 0,
                                   (getHeight() * (Propiedades.getFactor() * -1)));
                puntos = ". .";
                break;
              case Propiedades.ABAJO: //Leermos la inferior
                leerImagen =
                    new LeerImagen(getWidth(), getHeight(), 0,
                                   (getHeight() * Propiedades.getFactor()));
                puntos = ". . .";
                break;
              case Propiedades.DERECHA: //Leermos a la derecha
                leerImagen =
                    new LeerImagen(getWidth(), getHeight(),
                                   (getWidth() * Propiedades.getFactor()), 0);
                puntos = ".";
                break;
              case Propiedades.IZQUIERDA: //Leermos a la izquierda
                leerImagen =
                    new LeerImagen(getWidth(), getHeight(),
                                   (getWidth() * (Propiedades.getFactor() * -1)),
                                   0);
                puntos = ". .";
                break;
            }
          }
          else { //Termino de leer todas las imagenes
            termino = true;
            //Para hilo
            hiloArrancado = false;
            //Saca mensaje final
            repaint();
          }
        }
        else { //Imagen no se a terminado de leer
          if (leerImagen.isError()) {
            textError = leerImagen.getError();
            termino = true;
            hiloArrancado = false;
            Propiedades.setConsola("error_3:"+textError);
            ((ClienteHTTP) midlet).verConsola();
          }
          repaint();
        }

      }
      catch (Exception ex) {
        Propiedades.setConsola("Error_2");
      }
    }
  }

  /**
   * Eventos de teclado
   * @param keyCode Tecla presionada
   */
  protected void keyPressed(int keyCode) {
    if (termino) {
      ( (ClienteHTTP) midlet).mostrarMapa(mapasArray);
    }
  }

}