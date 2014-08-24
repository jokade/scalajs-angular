package com.greencatsoft.angularjs.route

import com.greencatsoft.angularjs.controller.SingleController

abstract class SingleTemplateController(val templateUrl: String,
                                        override val title: Option[String] = None,
                                        _name: String = null)
  extends SingleController(_name) with TemplateController
