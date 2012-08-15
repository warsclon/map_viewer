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
 * <p>Title: Controls</p>
 * <p>Description: Muestra un png con los controles de la aplicación</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Diego Martín Moreno(c) 2003</p>
 * @author Diego Martín Moreno
 * @version 1.0
 */
public class Controls
    extends Canvas
    implements CommandListener {

  //Midlet
  private MIDlet midlet;

  //pantalla donde se muestra el menu
  private Display display;

  //Imagen con los controles del mapa
  private Image image;

  //Comando salir para volver al menu
  static final Command salir = new Command("salir", Command.EXIT, 1);

  /**
   * Captura de comandos obligatorio para Canvas normal
   * @param c Teclado
   * @param d Pantalla
   */
  public void commandAction(Command c, Displayable d) {
    ( (ClienteHTTP) midlet).pantallaMenu();
  }

  /**
   * Eventos de teclado
   * @param keyCode Código del teclado
   */
  protected void keyPressed(int keyCode) {
    ( (ClienteHTTP) midlet).pantallaMenu();
  }

  /**
   * Pinta el png
   * @param g Graphics
   */
  public void paint(Graphics g) {
    g.setStrokeStyle(Graphics.SOLID);

    //blanco
    g.setColor(255, 255, 255);
    g.fillRect(0, 0, getHeight(), getWidth());
    //negro
    g.drawImage(
        image,
        (getWidth() / 2) - 45,
        (getHeight() / 2) - 25,
        Graphics.LEFT | Graphics.TOP);
  }

  /**
   * Contructor del controls
   * @param m Midlet
   */
  public Controls(MIDlet m) {
    try {
      image = Image.createImage("/menu.png");
    }
    catch (Exception ex) {
    }
    midlet = m;
  }

}