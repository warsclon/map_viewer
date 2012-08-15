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
 * <p>Title: ClienteHTTP </p>
 * <p>Description: Clase principal del cliente para visualizar mapas, para cualquier movil midp</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Diego Mart�n Moreno(c) 2003</p>
 * @author Diego Mart�n Moreno
 * @version 1.0
 */
public class ClienteHTTP
    extends MIDlet {

  //Clase con la presentaci�n inicial
  private Presentacion presentacion;

  //Clase que gestiona el movimiento del mapa
  private MoverMapa moverMapa;

  //Clase con informaci�n de descargas
  private Contador contador;

  //Clase con la consola de la aplicaci�n
  private Consola consola;

  //Clase con los controles
  private Controls controls;

  //Clase con el menu de la aplicaci�n
  private Menu menuOp;

  //Pantalla
  public static Display display;

  /**
   * Inicializa la clase aqui
   */
  public ClienteHTTP() {

    //inicializar fichero de propiedades
    new Propiedades(this);
    //Instancia objeto con presentaci�n con el logo de batmap
    presentacion = new Presentacion(this);
    contador = new Contador(this);
    controls = new Controls(this);
    //Consola movil
    Propiedades.setConsola("Inicio aplicacion");
  }

  /**
   * Muestra el contador de la aplicaci�n
   */
  public void verContador() {
    display.setCurrent(contador);
  }

  /**
   * Muestra el png con los controles de la aplicaci�n
   */
  public void verControl() {
    display.setCurrent(controls);
  }

  /**
   * Vuelve al mapa desde otra parte del programa
   */
  public void volverMapa() {
    display.setCurrent(moverMapa);
  }

  /**
   * Muestra la consola
   */
  public void verConsola() {
    consola = new Consola(this, display);
  }

  /**
   * Muestra en la pantalla el menu del juego
   */
  public void pantallaMenu() {
    menuOp = new Menu(display, this);
  }

  /**
   * Carga el mapa, con las 5 imagenes del comienzo
   */
  public void mostrarMapa(Image[] mapas) {

    moverMapa = new MoverMapa(this, mapas);
    display.setCurrent(moverMapa);

  }

  /**
   * Arranque de la aplicaci�n
   * @throws MIDletStateChangeException Excepci�n al arrancar la aplicaci�n
   */
  public void startApp() throws MIDletStateChangeException {
    display = Display.getDisplay(this);
    display.setCurrent(presentacion);
  }

  /**
   * Cierra la aplicaci�n
   * @param condicional pasar true para que se cierre
   */
  public void destroyApp(boolean condicional) {

  }

  /**
   * Pausa de la aplicaci�n
   */
  public void pauseApp() {

  }

}