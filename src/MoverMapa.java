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
 * <p>Title: MoverMapa </p>
 * <p>Description: Clase donde se gestionan el movimiento del mapa</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Diego Martín Moreno(c) 2003</p>
 * @author Diego Martín Moreno
 * @version 0.0.1
 */
public class MoverMapa
    extends Canvas
    implements Runnable {

  //Array con las 5 imagenes descargadas
  private Image[] mapas;

  //Hilo para leer mas imagenes
  private LeerImagen leerImagen;

  //Para si no tiene doble buffer de imagen
  private Image offscreen = null;

  //Midlet
  private MIDlet midlet;

  //Tiempo de espera de la animación
  private int sleepTh = 200;

  //Control hilo del mapa
  private boolean hiloArrancado = true;

  //Control para desplazamiento continuo
  private boolean isRepeatedKey;

  //Ultima tecla presionada
  private int lastKeyPressed;

  //Hilo del mapa
  private Thread hiloMapa;

  //Control mostrando cargando mapa
  private boolean cargando = false;

  //Posicion X movimiento
  private int posX;

  //Posicion Y movimiento
  private int posY;

  //Control del tipo de moviento
  private int tipoMovimiento;

  //Control punto para indicar la carga de un mapa
  private boolean punto;

  //Control punto para la carga de un mapa
  private boolean iniciarCarga;

  //Control para pintar la primera vez q entramos
  private boolean inicio = true;

  ////controles para pedir mapas
  //Control cuando llega al limite del mapa
  private boolean llegaLimite;

  //Desplazamiento de las imagenes en X
  private int movimientoImagenesX;

  //Desplazamiento de las imagenes en Y
  private int movimientoImagenesY;

  //Imagen temporal para desplazamientos
  private Image tempIMage;

  //Control si ya no es el inicio de la aplicacion
  private boolean inicioNo;

  //Factor de desplazamiento
  private int factorDesp = 1;

  //Factor de desplazamiento del centro
  private int factorDespCentroX = 0;

  //Factor de desplazamiento del centro
  private int factorDespCentroY = 0;

  private int ultimaPresionada;

  //Control cuando tiene cambio de sentido de la vertical a la horizontal
  private boolean cambioSentidoHorizontal;

  //Control cuando tiene cambio de sentido de la horizontal a la vertical
  private boolean cambioSentidoVertical;

  private int contador = 1;

  /**
   * Contructor del gestor de los mapas
   * @param aplicacion Midlet de la aplicacion
   * @param maps Array con las imagenes de los mapas cargados
   */
  public MoverMapa(MIDlet aplicacion, Image[] maps) {

    midlet = aplicacion;

    mapas = maps;

    //Comprueba si tiene doble buffer la imagen
    if (!isDoubleBuffered()) {
      offscreen = Image.createImage(getWidth(), getHeight());
    }

    try {
      hiloMapa = new Thread(this);
    }
    catch (Exception ex) {
      Propiedades.setConsola("Error_4:" + ex);
    }
    hiloMapa.start();
  }

  /**
   * Pinta el mapa
   * @param g Objeto graphics
   */
  public void paint(Graphics g) {

    Graphics saved = g;
    if (offscreen != null) {
      g = offscreen.getGraphics();
    }

    if (!llegaLimite && !cambioSentidoVertical && !cambioSentidoHorizontal) { //movimiento mapa
      //color blanco
      g.setColor(255, 255, 255);
      g.fillRect(0, 0, getWidth(), getHeight());

      //Pinta mapa
      if (inicio) {
        MoverPantalla.inicio(g, mapas);
        inicio = false;
      }

      switch (tipoMovimiento) {
        case Propiedades.ARRIBA: //Mueve arriba
        case Propiedades.ABAJO: //Mueve arriba
          MoverPantalla.movimientoVertical(g, mapas, this, 0,
                                           movimientoImagenesY);
          break;
        case Propiedades.DERECHA: //Mueve arriba
        case Propiedades.IZQUIERDA: //Mueve arriba
          MoverPantalla.movimientoHorizontal(g, mapas, this,
                                             movimientoImagenesX, 0);
          break;
      }
    }
    else { //mostar cargando en la pantalla
      if (punto) {
        Mensajes.loadingSinPunto(g, this);
        punto = false;
      }
      else {
        Mensajes.loadingConPunto(g, this);
        punto = true;
      }
    }

    if (g != saved) {
      saved.drawImage(offscreen, 0, 0, Graphics.LEFT | Graphics.TOP);
    }

  }

  /**
   * Hilo del mapa para mostrar los mensajes de carga y el movimiento continuo
   */
  public void run() {
    while (hiloArrancado) {

      try { //Para el hilo
        hiloMapa.sleep(sleepTh);
      }
      catch (Exception ex) {
        Propiedades.setConsola("Error_5:" + ex);
      }

      if (llegaLimite) { //pedir imagen nueva
        if (!iniciarCarga) { //control para lanzar el hilo por primera vez
          lanzarHilo();
          iniciarCarga = true;
        }

        if (leerImagen.imagenLeida()) { //fin carga del mapa
          cambiarImagen();
          llegaLimite = false;
          iniciarCarga = false;
        }

        try {
          if (leerImagen.getError() != null) {
            llegaLimite = false;
            iniciarCarga = false;
            Propiedades.setConsola(leerImagen.getError());
          }
        }
        catch (Exception ex) {
        }

        //Pinta mensaje loading
        repaint();

      }

      if (cambioSentidoVertical) {
        if (!iniciarCarga) { //control para lanzar el hilo por primera vez
          getImagenCentral();
          iniciarCarga = true;
        }
        if (leerImagen.imagenLeida() && contador == 1) {
          setImagenCentral(leerImagen.getMapa());
          getImagenSuperior();
          contador++;
        }

        if (leerImagen.imagenLeida() && contador == 2) {
          setImagenSuperior(leerImagen.getMapa());
          getImagenInferior();
          contador++;
        }

        if (leerImagen.imagenLeida() && contador == 3) {
          setImagenInferior(leerImagen.getMapa());
          cambioSentidoVertical = false;
          iniciarCarga = false;
          contador = 1;
          movimientoImagenesY = 0;
          movimientoImagenesX = 0;
          ultimaPresionada = Propiedades.ABAJO;
        }

        repaint();
      }

      if (cambioSentidoHorizontal) {
        if (!iniciarCarga) { //control para lanzar el hilo por primera vez
          getImagenCentral();
          iniciarCarga = true;
        }
        if (leerImagen.imagenLeida() && contador == 1) {
          setImagenCentral(leerImagen.getMapa());
          getImagenDerecha();
          contador++;
        }

        if (leerImagen.imagenLeida() && contador == 2) {
          setImagenDerecha(leerImagen.getMapa());
          getImagenIzquierda();
          contador++;
        }
        if (leerImagen.imagenLeida() && contador == 3) {
          setImagenIzquierda(leerImagen.getMapa());
          cambioSentidoHorizontal = false;
          iniciarCarga = false;
          contador = 1;
          movimientoImagenesY = 0;
          movimientoImagenesX = 0;
          ultimaPresionada = Propiedades.DERECHA;
        }

        repaint();
      }

      else { //cuando no esta en el limite si recibe eventos

        //Control boton continuo
        if (isRepeatedKey) {
          switch (lastKeyPressed) {
            case -2: //abajo emulador
            case Canvas.DOWN: //abajo emulador
              if ( (!cambioSentidoHorizontal && !cambioSentidoVertical) &&
                  !llegaLimite) {
                //posicion central
                posY += 10;
                //posicion imagenes
                movimientoImagenesY -= 10;
                ultimaPresionada = Propiedades.ABAJO;
                tipoMovimiento = Propiedades.ABAJO;
              }
              repaint();
              break;
            case -1: //arriba emulador
            case Canvas.UP: //arriba emulador
              if ( (!cambioSentidoHorizontal && !cambioSentidoVertical) &&
                  !llegaLimite) {
                posY -= 10;
                movimientoImagenesY += 10;
                ultimaPresionada = Propiedades.ARRIBA;
                tipoMovimiento = Propiedades.ARRIBA;
              }
              repaint();
              break;
            case -3: //izquierda emulador
            case Canvas.LEFT: //izquierda emulador
              if ( (!cambioSentidoHorizontal && !cambioSentidoVertical) &&
                  !llegaLimite) {
                posX -= 10;
                movimientoImagenesX += 10;
                ultimaPresionada = Propiedades.IZQUIERDA;
                tipoMovimiento = Propiedades.IZQUIERDA;
              }
              repaint();
              break;
            case -4: //derecha emulador
            case Canvas.RIGHT: //derecha emulador
              if ( (!cambioSentidoHorizontal && !cambioSentidoVertical) &&
                  !llegaLimite) {
                posX += 10;
                movimientoImagenesX -= 10;
                ultimaPresionada = Propiedades.DERECHA;
                tipoMovimiento = Propiedades.DERECHA;
              }
              repaint();
              break;
          }
          //
          //Control cuando se llega al límite de un mapa en la pantalla
          switch (tipoMovimiento) {
            case -2: //abajo emulador
            case Propiedades.ABAJO:
              if ( ( (getHeight() - Math.abs(movimientoImagenesY)) < 10) &&
                  (movimientoImagenesY < 0) || cambioSentidoVertical) { //limite = largo de la pantalla
                llegaLimite = true;
              }
              else {
                repaint();
              }

              break;
            case -1: //arriba emulador
            case Propiedades.ARRIBA:
              if ( (getHeight() - movimientoImagenesY) < 10 ||
                  cambioSentidoVertical) { //limite = largo de la pantalla
                llegaLimite = true;
              }
              else {
                repaint();
              }
              break;
            case -4: //izquierda emulador
            case Propiedades.DERECHA:
              if ( ( (getWidth() - Math.abs(movimientoImagenesX)) < 10) &&
                  (movimientoImagenesX < 0) || cambioSentidoVertical) { //limite = ancho de la pantalla
                llegaLimite = true;
              }
              else {
                repaint();
              }
              break;
            case -3: //izquierda emulador
            case Propiedades.IZQUIERDA:
              if ( (getWidth() - movimientoImagenesX) < 10 ||
                  cambioSentidoVertical) { //limite = ancho de la pantalla
                llegaLimite = true;
              }
              else {
                repaint();
              }
              break;
          } //Fin control limite del mapa

          //
        }
        isRepeatedKey = false;
        lastKeyPressed = 0;
      }
    }

  }

  public void keyReleased(int keyCode) {
  }

  protected void keyRepeated(int keyCode) {
    isRepeatedKey = true;
    lastKeyPressed = keyCode;
  }

  /**
   * Eventos de teclado
   * @param keyCode Tecla presionada
   */
  protected void keyPressed(int keyCode) {

    //Control de cambio de sentido
    if (ultimaPresionada != 0) { //No es el inicio
      int presionado = 0;
      switch (keyCode) { //Vemos la ultima tecla presionada
        case -2: //abajo emulador
        case Canvas.DOWN: //abajo emulador
          presionado = Propiedades.ABAJO;
          break;
        case -1: //arriba emulador
        case Canvas.UP: //arriba emulador
          presionado = Propiedades.ARRIBA;
          break;
        case -3: //izquierda emulador
        case Canvas.LEFT: //izquierda emulador
          presionado = Propiedades.IZQUIERDA;
          break;
        case -4: //derecha emulador
        case Canvas.RIGHT: //derecha emulador
          presionado = Propiedades.DERECHA;
          break;
      }
      if (ultimaPresionada == Propiedades.ABAJO ||
          ultimaPresionada == Propiedades.ARRIBA) { // sentido vertical - cambio a horizontal
        if (presionado == Propiedades.DERECHA ||
            presionado == Propiedades.IZQUIERDA) {
          cambioSentidoHorizontal = true;
        }
      }
      else {
        if (presionado == Propiedades.ARRIBA || presionado == Propiedades.ABAJO) {
          cambioSentidoVertical = true;
        }
      }
    }
    //fin control cambio de sentido

    //Si tienes cambio de sentido no puede mover mapa
    if ( (!cambioSentidoHorizontal && !cambioSentidoVertical) && !llegaLimite) {
      switch (keyCode) {
        case Canvas.KEY_NUM1: //KEY_SOFTKEY2: para salir
          ( (ClienteHTTP) midlet).pantallaMenu();
          break;

        case -2: //abajo emulador
        case Canvas.DOWN: //abajo emulador

          //posicion central
          posY += 10;
          //posicion imagenes
          movimientoImagenesY -= 10;
          ultimaPresionada = Propiedades.ABAJO;
          tipoMovimiento = Propiedades.ABAJO;
          break;

        case -1: //arriba emulador
        case Canvas.UP: //arriba emulador

          posY -= 10;
          movimientoImagenesY += 10;
          ultimaPresionada = Propiedades.ARRIBA;
          tipoMovimiento = Propiedades.ARRIBA;
          break;

        case -3: //izquierda emulador
        case Canvas.LEFT: //izquierda emulador

          posX -= 10;
          movimientoImagenesX += 10;
          ultimaPresionada = Propiedades.IZQUIERDA;
          tipoMovimiento = Propiedades.IZQUIERDA;
          break;

        case -4: //derecha emulador
        case Canvas.RIGHT: //derecha emulador

          posX += 10;
          movimientoImagenesX -= 10;
          ultimaPresionada = Propiedades.DERECHA;
          tipoMovimiento = Propiedades.DERECHA;
          break;
      }

      //Control cuando se llega al límite de un mapa en la pantalla
      switch (tipoMovimiento) {
        case -2: //abajo emulador
        case Propiedades.ABAJO:
          if ( ( (getHeight() - Math.abs(movimientoImagenesY)) < 10) &&
              (movimientoImagenesY < 0) || cambioSentidoVertical) { //limite = largo de la pantalla
            llegaLimite = true;
          }
          else {
            repaint();
          }

          break;
        case -1: //arriba emulador
        case Propiedades.ARRIBA:
          if ( (getHeight() - movimientoImagenesY) < 10 ||
              cambioSentidoVertical) { //limite = largo de la pantalla
            llegaLimite = true;
          }
          else {
            repaint();
          }
          break;
        case -4: //izquierda emulador
        case Propiedades.DERECHA:
          if ( ( (getWidth() - Math.abs(movimientoImagenesX)) < 10) &&
              (movimientoImagenesX < 0) || cambioSentidoVertical) { //limite = ancho de la pantalla
            llegaLimite = true;
          }
          else {
            repaint();
          }
          break;
        case -3: //izquierda emulador
        case Propiedades.IZQUIERDA:
          if ( (getWidth() - movimientoImagenesX) < 10 ||
              cambioSentidoVertical) { //limite = ancho de la pantalla
            llegaLimite = true;
          }
          else {
            repaint();
          }
          break;
      } //Fin control limite del mapa
    } //Fin no ha cambio de sentido

  }

  /**
   * Cmabia las imagenes en base al movimiento por el mapa
   */
  public void cambiarImagen() {
    switch (ultimaPresionada) {
      case -2: //abajo emulador
      case Propiedades.ABAJO:
        tempIMage = mapas[Propiedades.ABAJO]; //
        mapas[Propiedades.ABAJO] = leerImagen.getMapa();
        leerImagen.cleanMapa();
        mapas[Propiedades.ARRIBA] = mapas[Propiedades.CENTRAL];
        mapas[Propiedades.CENTRAL] = tempIMage;
        tempIMage = null;
        leerImagen = null;

        //Inicializar variable pintar mapa
        movimientoImagenesY = 0;

        break;
      case -1: //arriba emulador
      case Propiedades.ARRIBA:
        tempIMage = mapas[Propiedades.ARRIBA]; //
        mapas[Propiedades.ARRIBA] = leerImagen.getMapa();
        leerImagen.cleanMapa();
        mapas[Propiedades.ABAJO] = mapas[Propiedades.CENTRAL];
        mapas[Propiedades.CENTRAL] = tempIMage;
        tempIMage = null;
        leerImagen = null;

        //Inicializar variable pintar mapa
        movimientoImagenesY = 0;

        break;
      case -4: //derecha emulador
      case Propiedades.DERECHA:
        tempIMage = mapas[Propiedades.DERECHA]; //
        mapas[Propiedades.DERECHA] = leerImagen.getMapa();
        leerImagen.cleanMapa();
        mapas[Propiedades.IZQUIERDA] = mapas[Propiedades.CENTRAL];
        mapas[Propiedades.CENTRAL] = tempIMage;
        tempIMage = null;
        leerImagen = null;

        //Inicializar variable pintar mapa
        movimientoImagenesX = 0;

        break;
      case -3: //izquierda emulador
      case Propiedades.IZQUIERDA:
        tempIMage = mapas[Propiedades.IZQUIERDA]; //
        mapas[Propiedades.IZQUIERDA] = leerImagen.getMapa();
        leerImagen.cleanMapa();
        mapas[Propiedades.DERECHA] = mapas[Propiedades.CENTRAL];
        mapas[Propiedades.CENTRAL] = tempIMage;
        tempIMage = null;
        leerImagen = null;

        //Inicializar variable pintar mapa
        movimientoImagenesX = 0;

        break;
    }

  }

  /**
   * Obtiene la imagen central de la pantalla
   */
  public void getImagenCentral() {
    llamarHilo( (posX * Propiedades.getFactor()),
               (posY * Propiedades.getFactor()));
  }

  /**
   * Pone la imagen cargada en parte central del mapa
   * @param image Imagen cargada
   */
  public void setImagenCentral(Image image) {
    mapas[Propiedades.CENTRAL] = image;
  }

  /**
   * Obtiene la imagen superior de la pantalla
   */
  public void getImagenSuperior() {
    int tempY = ( (posY * Propiedades.getFactor()) -
                 (getHeight() * Propiedades.getFactor())); //para que sea nevativo
    llamarHilo(posX * Propiedades.getFactor(), tempY);
  }

  /**
   * Pone la imagen cargada en parte superior del mapa
   * @param image Imagen cargada
   */
  public void setImagenSuperior(Image image) {
    mapas[Propiedades.ARRIBA] = image;
  }

  /**
   * Obtiene la imagen inferior de la pantalla
   */
  public void getImagenInferior() {
    int tempY = ( (posY * Propiedades.getFactor()) +
                 (getHeight() * Propiedades.getFactor())); //para que sea positivo
    llamarHilo(posX * Propiedades.getFactor(), tempY);
  }

  /**
   * Pone la imagen cargada en parte inferior del mapa
   * @param image Imagen cargada
   */
  public void setImagenInferior(Image image) {
    mapas[Propiedades.ABAJO] = image;
  }

  /**
   * Obtiene la imagen derecha de la pantalla
   */
  public void getImagenDerecha() {
    int tempX = ( (posX * Propiedades.getFactor()) +
                 (getWidth() * Propiedades.getFactor()));
    llamarHilo(tempX, (posY * Propiedades.getFactor()));
  }

  /**
   * Pone la imagen cargada en parte derecha del mapa
   * @param image Imagen cargada
   */
  public void setImagenDerecha(Image image) {
    mapas[Propiedades.DERECHA] = image;
  }

  /**
   * Obtiene la imagen izquierda de la pantalla
   */
  public void getImagenIzquierda() {
    int tempX = ( (posX * Propiedades.getFactor()) -
                 (getWidth() * Propiedades.getFactor()));
    llamarHilo(tempX, (posY * Propiedades.getFactor()));
  }

  /**
   * Pone la imagen cargada en parte izquierda del mapa
   * @param image Imagen cargada
   */
  public void setImagenIzquierda(Image image) {
    mapas[Propiedades.IZQUIERDA] = image;
  }

  /**
   * Método que pide un mapa mas cuando se llega al limite
   */
  public void lanzarHilo() {
    switch (ultimaPresionada) {
      case -2: //abajo emulador
      case Propiedades.ABAJO:
        getImagenInferior();
        break;
      case -1: //arriba emulador
      case Propiedades.ARRIBA:

        getImagenSuperior();
        break;
      case -4: //derecha emulador
      case Propiedades.DERECHA:
        getImagenDerecha();
        break;
      case -3: //izquierda emulador
      case Propiedades.IZQUIERDA:
        getImagenIzquierda();
        break;
    }

  }

  /**
   * Método para pedir una nueva imagen con el tamaño de la pantalla
   * @param x Posición X
   * @param y Posición Y
   */
  public void llamarHilo(int x, int y) {
    leerImagen =
        new LeerImagen(getWidth(), getHeight(), x, y);
  }
}
