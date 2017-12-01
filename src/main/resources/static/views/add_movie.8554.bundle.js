webpackJsonp([3],{

/***/ 14:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_react__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_react___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_react__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_react_ui_lib_form_EmptyForm_js__ = __webpack_require__(15);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_react_ui_lib_form_EmptyForm_js___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_react_ui_lib_form_EmptyForm_js__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__base_TextInput_jsx__ = __webpack_require__(19);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__js_AjaxUtil_js__ = __webpack_require__(20);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__js_URL_js__ = __webpack_require__(21);





class AddMovie extends __WEBPACK_IMPORTED_MODULE_1_react_ui_lib_form_EmptyForm_js___default.a {
  constructor(...args) {
    var _temp;

    return _temp = super(...args), this.state = {
      actress: '',
      code: '',
      label: '',
      level: 3,
      path: ''
    }, this.handleClick = () => {
      __WEBPACK_IMPORTED_MODULE_3__js_AjaxUtil_js__["a" /* default */].post(__WEBPACK_IMPORTED_MODULE_4__js_URL_js__["a" /* default */].movie, this.state, function (data) {
        alert(JSON.stringify(data));
      });
    }, this.createCode = () => {
      if (this.state.path) {
        let index1 = this.state.path.lastIndexOf("\\");
        let index2 = this.state.path.lastIndexOf("/");
        let index = Math.max(index1, index2) + 1;
        let pointIndex = this.state.path.lastIndexOf(".");
        let code = this.state.path.substring(index, pointIndex).toUpperCase();
        const newState = { code: code };
        this.setState(newState);
      }
    }, _temp;
  }

  render() {
    const pageStyle = {
      display: 'flex',
      justifyContent: 'center'
    };
    return __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(
      'div',
      { style: pageStyle },
      __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(
        'div',
        null,
        __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(__WEBPACK_IMPORTED_MODULE_2__base_TextInput_jsx__["a" /* default */], { obj: this, name: 'actress', label: 'actress\uFF1A' }),
        __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(__WEBPACK_IMPORTED_MODULE_2__base_TextInput_jsx__["a" /* default */], { obj: this, name: 'code', label: 'code\uFF1A' }),
        __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(__WEBPACK_IMPORTED_MODULE_2__base_TextInput_jsx__["a" /* default */], { obj: this, name: 'label', label: 'label\uFF1A' }),
        __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(__WEBPACK_IMPORTED_MODULE_2__base_TextInput_jsx__["a" /* default */], { obj: this, name: 'level', label: 'level\uFF1A' }),
        __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(__WEBPACK_IMPORTED_MODULE_2__base_TextInput_jsx__["a" /* default */], { obj: this, name: 'path', label: '\u89C6\u9891\u6587\u4EF6\u8DEF\u5F84\uFF1A' }),
        __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(
          'div',
          { style: pageStyle },
          __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(
            'button',
            { onClick: this.createCode },
            '\u751F\u6210CODE'
          ),
          __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(
            'button',
            { onClick: this.handleClick },
            '\u6DFB\u52A0'
          )
        )
      )
    );
  }
}

/* harmony default export */ __webpack_exports__["a"] = (AddMovie);

/***/ }),

/***/ 15:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _react = __webpack_require__(16);

var _react2 = _interopRequireDefault(_react);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

/**空白表单对象，实现了handleChange方法,
*handleTextChange是配合文本输入框使用的方法，可以更改state对象
*/
var EmptyForm = function (_React$Component) {
    _inherits(EmptyForm, _React$Component);

    function EmptyForm() {
        var _ref;

        var _temp, _this, _ret;

        _classCallCheck(this, EmptyForm);

        for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
            args[_key] = arguments[_key];
        }

        return _ret = (_temp = (_this = _possibleConstructorReturn(this, (_ref = EmptyForm.__proto__ || Object.getPrototypeOf(EmptyForm)).call.apply(_ref, [this].concat(args))), _this), _this.handleTextChange = function (event) {
            event.preventDefault();
            var newState = {};
            var name = event.target.name;
            newState[name] = event.target.value;
            _this.setState(newState);
        }, _temp), _possibleConstructorReturn(_this, _ret);
    }

    return EmptyForm;
}(_react2.default.Component);

exports.default = EmptyForm;

/***/ }),

/***/ 19:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_react__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_react___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_react__);

/**基本的文本输入框，输入框需要提供：label、name、obj三个属性
*label为输入框标签，name对应obj对象state参数相应KEY,
*obj为调用此输入框的组件，obj需要有handleChange方法或者继承handleChange方法
*/
class TextInput extends __WEBPACK_IMPORTED_MODULE_0_react___default.a.Component {
  constructor(...args) {
    var _temp;

    return _temp = super(...args), this.isAddModel = () => {
      return true;
    }, this.getType = () => {
      return 'text';
    }, _temp;
  }

  render() {
    let obj = this.props.obj;
    const name = this.props.name;
    const label = this.props.label;
    const labelWidth = this.props.labelWidth ? this.props.labelWidth : 200;
    const inputWidth = this.props.inputWidth ? this.props.inputWidth : 500;
    const labelStyle = {
      display: 'inline-flex',
      justifyContent: 'flex-end',
      width: labelWidth
    };
    const inputStyle = {
      display: 'inline-flex',
      width: inputWidth,
      borderColor: '#000',
      borderWidth: 1,
      borderStyle: 'solid',
      padding: 1
    };
    let disabled = this.isAddModel();
    const type = this.getType();
    const lineStyle = {
      display: 'flex',
      marginBottom: 5
    };
    return __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(
      'div',
      { style: lineStyle },
      __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(
        'span',
        { style: labelStyle },
        label
      ),
      __WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement('input', { style: inputStyle, type: type, disabled: !disabled, name: name, value: obj.state[name], onChange: obj.handleTextChange })
    );
  }
}

/* harmony default export */ __webpack_exports__["a"] = (TextInput);

/***/ }),

/***/ 20:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
class AjaxUtil {
  static get(url, callSuccess) {
    fetch(url).then(response => response.json()).then(json => {
      if (json.errorResponse) {
        alert(json.message);
      } else {
        callSuccess(json);
      }
    });
  }
  static post(url, data, callSuccess) {
    var myRequest = this.createRequest('POST', url, data);
    this.f(myRequest, callSuccess);
  }
  static put(url, data, callSuccess) {
    var myRequest = this.createRequest('PUT', url, data);
    this.f(myRequest, callSuccess);
  }
  static delete(url, data, callSuccess) {
    var myRequest = this.createRequest('DELETE', url, data);
    this.f(myRequest, callSuccess);
  }
  static f(myRequest, callSuccess) {
    fetch(myRequest).then(response => response.json()).then(json => {
      if (json.errorResponse) {
        alert(json.message);
      } else {
        callSuccess(json);
      }
    });
  }
  static createRequest(method, url, data) {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    var myInit = {
      method: method,
      headers: myHeaders,
      cache: 'default',
      body: JSON.stringify(data)
    };
    return new Request(url, myInit);
  }
}

/* harmony default export */ __webpack_exports__["a"] = (AjaxUtil);

/***/ }),

/***/ 21:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
const url = {
  movie: '/movie',
  movies: '/movies'
};
/* harmony default export */ __webpack_exports__["a"] = (url);

/***/ }),

/***/ 47:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_react__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_react___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_react__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_react_dom__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_react_dom___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_react_dom__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__jsx_AddMovie_jsx__ = __webpack_require__(14);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_react_ui_lib_base_PageTitle_js__ = __webpack_require__(22);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_react_ui_lib_base_PageTitle_js___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_react_ui_lib_base_PageTitle_js__);





__WEBPACK_IMPORTED_MODULE_1_react_dom___default.a.render(__WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(__WEBPACK_IMPORTED_MODULE_3_react_ui_lib_base_PageTitle_js___default.a, { title: '\u6DFB\u52A0\u89C6\u9891' }), document.getElementById('page-title'));
__WEBPACK_IMPORTED_MODULE_1_react_dom___default.a.render(__WEBPACK_IMPORTED_MODULE_0_react___default.a.createElement(__WEBPACK_IMPORTED_MODULE_2__jsx_AddMovie_jsx__["a" /* default */], null), document.getElementById('main-ui'));

/***/ })

},[47]);