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
 * <p>Title: Menu</p>
 * <p>Description: Menu de a la aplicación</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Diego Martín Moreno(c) 2003</p>
 * @author Diego Martín Moreno
 * @version 1.0
 */
public class Menu
    extends Form
    implements CommandListener {

  //opciones del menu
  private String opciones[] = {
      "Accountant", "Controls", "Map", "Console"};

  //Lista del menu
  private List mOpciones = new List("Menu", Choice.IMPLICIT, opciones, null);

  //Midlet para llamar a los mapas
  private MIDlet midlet;

  //pantalla donde se muestra el menu
  private Display display;

  //Comando salir de la aplicación
  private static final Command salir = new Command("exit", Command.EXIT, 1);

  /**
   * Constructor del menu
   * @param d Display para mostrar el menu
   * @param m Midlet para llamar a otras clases
   */
  public Menu(Display d, MIDlet m) {
    super("Menu");
    display = d;
    midlet = m;
    mOpciones.addCommand(salir);
    mOpciones.setCommandListener(this);
    d.setCurrent(mOpciones);
  }

  /**
   * Captura evento del teclado del menu
   * @param c Teclado
   * @param d Pantalla
   */
  public void commandAction(Command c, Displayable d) {
    if (c == List.SELECT_COMMAND) {
      switch (mOpciones.getSelectedIndex()) {
        case 0:
          ( (ClienteHTTP) midlet).verContador();
          break;
        case 1:
          ( (ClienteHTTP) midlet).verControl();
          break;
        case 2:
          ( (ClienteHTTP) midlet).volverMapa();
          break;
        case 3:
          ( (ClienteHTTP) midlet).verConsola();
          break;
      }
    }
  }
}