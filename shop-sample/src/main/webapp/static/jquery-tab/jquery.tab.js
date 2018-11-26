(function(t){t.fn.ligerTab=function(e){return t.ligerui.run.call(this,"ligerTab",arguments)};t.fn.ligerGetTabManager=function(){return t.ligerui.run.call(this,"ligerGetTabManager",arguments)};t.ligerDefaults.Tab={height:null,heightDiff:0,changeHeightOnResize:false,contextmenu:true,dblClickToClose:true,dragToMove:false,onBeforeOverrideTabItem:null,onAfterOverrideTabItem:null,onBeforeRemoveTabItem:null,onAfterRemoveTabItem:null,onBeforeAddTabItem:null,onAfterAddTabItem:null,onBeforeSelectTabItem:null,onAfterSelectTabItem:null,onAfterLeaveTabItem:null};t.ligerDefaults.TabString={closeMessage:"关闭当前页",closeOtherMessage:"关闭其他",closeAllMessage:"关闭所有",reloadMessage:"刷新"};t.ligerMethos.Tab={};t.ligerui.controls.Tab=function(e,i){t.ligerui.controls.Tab.base.constructor.call(this,e,i)};t.ligerui.controls.Tab.ligerExtend(t.ligerui.core.UIComponent,{__getType:function(){return"Tab"},__idPrev:function(){return"Tab"},_extendMethods:function(){return t.ligerMethos.Tab},_render:function(){var e=this,i=this.options;if(i.height)e.makeFullHeight=true;e.tab=t(this.element);e.tab.addClass("l-tab");if(i.contextmenu&&t.ligerMenu){e.tab.menu=t.ligerMenu({width:100,items:[{text:i.closeMessage,id:"close",click:function(){e._menuItemClick.apply(e,arguments)}},{text:i.closeOtherMessage,id:"closeother",click:function(){e._menuItemClick.apply(e,arguments)}},{text:i.closeAllMessage,id:"closeall",click:function(){e._menuItemClick.apply(e,arguments)}},{text:i.reloadMessage,id:"reload",click:function(){e._menuItemClick.apply(e,arguments)}}]})}e.tab.content=t('<div class="l-tab-content"></div>');t("> div",e.tab).appendTo(e.tab.content);e.tab.content.appendTo(e.tab);e.tab.links=t('<div class="l-tab-links"><ul style="left: 0px; "></ul></div>');e.tab.links.prependTo(e.tab);e.tab.links.ul=t("ul",e.tab.links);var a=t("> div[lselected=true]",e.tab.content);var n=a.length>0;e.selectedTabId=a.attr("tabid");t("> div",e.tab.content).each(function(i,a){var r=t('<li class=""><a unselectable="on"></a><div class="l-tab-links-item-left"></div><div class="l-tab-links-item-right"></div></li>');var l=t(this);if(l.attr("title")){t("> a",r).html(l.attr("title"));l.attr("title","")}var s=l.attr("tabid");if(s==undefined){s=e.getNewTabid();l.attr("tabid",s);if(l.attr("lselected")){e.selectedTabId=s}}r.attr("tabid",s);if(!n&&i==0)e.selectedTabId=s;var o=l.attr("showClose");if(o){r.append("<div class='l-tab-links-item-close'></div>")}t("> ul",e.tab.links).append(r);if(!l.hasClass("l-tab-content-item"))l.addClass("l-tab-content-item");if(l.find("iframe").length>0){var d=t("iframe:first",l);if(d[0].readyState!="complete"){if(l.find(".l-tab-loading:first").length==0)l.prepend("<div class='l-tab-loading' style='display:block;'></div>");var b=t(".l-tab-loading:first",l);d.bind("load.tab",function(){b.hide()})}}});e.selectTabItem(e.selectedTabId);if(i.height){if(typeof i.height=="string"&&i.height.indexOf("%")>0){e.onResize();if(i.changeHeightOnResize){t(window).resize(function(){e.onResize.call(e)})}}else{e.setHeight(i.height)}}if(e.makeFullHeight)e.setContentHeight();t("li",e.tab.links).each(function(){e._addTabItemEvent(t(this))});e.tab.bind("dblclick.tab",function(a){if(!i.dblClickToClose)return;e.dblclicking=true;var n=a.target||a.srcElement;var r=n.tagName.toLowerCase();if(r=="a"){var l=t(n).parent().attr("tabid");var s=t(n).parent().find("div.l-tab-links-item-close").length?true:false;if(s){e.removeTabItem(l)}}e.dblclicking=false});e.set(i)},_applyDrag:function(e){var i=this,a=this.options;i.droptip=i.droptip||t("<div class='l-tab-drag-droptip' style='display:none'><div class='l-drop-move-up'></div><div class='l-drop-move-down'></div></div>").appendTo("body");var n=t(e).ligerDrag({revert:true,animate:false,proxy:function(){var e=t(this).find("a").html();i.dragproxy=t("<div class='l-tab-drag-proxy' style='display:none'><div class='l-drop-icon l-drop-no'></div></div>").appendTo("body");i.dragproxy.append(e);return i.dragproxy},onRendered:function(){this.set("cursor","pointer")},onStartDrag:function(i,a){if(!t(e).hasClass("l-selected"))return false;if(a.button==2)return false;var n=a.srcElement||a.target;if(t(n).hasClass("l-tab-links-item-close"))return false},onDrag:function(e,a){if(i.dropIn==null)i.dropIn=-1;var n=i.tab.links.ul.find(">li");var r=n.index(e.target);n.each(function(e,n){if(r==e){return}var l=e>r;if(i.dropIn!=-1&&i.dropIn!=e)return;var s=t(this).offset();var o={top:s.top,bottom:s.top+t(this).height(),left:s.left-10,right:s.left+10};if(l){o.left+=t(this).width();o.right+=t(this).width()}var d=a.pageX||a.screenX;var b=a.pageY||a.screenY;if(d>o.left&&d<o.right&&b>o.top&&b<o.bottom){i.droptip.css({left:o.left+5,top:o.top-9}).show();i.dropIn=e;i.dragproxy.find(".l-drop-icon").removeClass("l-drop-no").addClass("l-drop-yes")}else{i.dropIn=-1;i.droptip.hide();i.dragproxy.find(".l-drop-icon").removeClass("l-drop-yes").addClass("l-drop-no")}})},onStopDrag:function(e,a){if(i.dropIn>-1){var n=i.tab.links.ul.find(">li:eq("+i.dropIn+")").attr("tabid");var r=t(e.target).attr("tabid");setTimeout(function(){i.moveTabItem(r,n)},0);i.dropIn=-1;i.dragproxy.remove()}i.droptip.hide();this.set("cursor","default")}});return n},_setDragToMove:function(e){if(!t.fn.ligerDrag)return;var i=this,a=this.options;if(e){if(i.drags)return;i.drags=i.drags||[];i.tab.links.ul.find(">li").each(function(){i.drags.push(i._applyDrag(this))})}},moveTabItem:function(t,e){var i=this;var a=i.tab.links.ul.find(">li[tabid="+t+"]");var n=i.tab.links.ul.find(">li[tabid="+e+"]");var r=i.tab.links.ul.find(">li").index(a);var l=i.tab.links.ul.find(">li").index(n);if(r<l){n.after(a)}else{n.before(a)}},setTabManageEven:function(){var e=this,i=this.options;t("#tabManage").click(function(){var i=t(this).offset();if(t(".l-tab-menu").length===0){var a='<div class="l-tab-menu">'+'<p id="tabCloseAll" data-opt="closeall"><b></b>关闭全部</p>'+'<p id="tabCloseCur" data-opt="closecur"><b></b>关闭当前页</p>'+'<p id="tabRefCur" data-opt="reloadcur"><b></b>刷新当前页</p>';"</div>";t("#page-tab").append(a);t(".l-tab-menu").css({top:i.top+30+"px",left:i.left-t(".l-tab-menu").outerWidth()+t("#tabManage").outerWidth()+"px"});t(".l-tab-menu p").each(function(i,a){t(this).click(function(){e._menuItemClick({id:t(this).data("opt")});t(".l-tab-menu").hide()})})}else{t(".l-tab-menu").css({top:i.top+30+"px",left:i.left-t(".l-tab-menu").outerWidth()+t("#tabManage").outerWidth()+"px"}).show()}});t(document).click(function(e){if(!(t(e.target).isChildAndSelfOf(".l-tab-menu")||t(e.target).isChildAndSelfOf("#tabManage"))){t(".l-tab-menu").hide()}})},setTabButton:function(){var e=this,i=this.options;var a=0;t("li",e.tab.links.ul).each(function(){a+=t(this).outerWidth()+parseInt(t(this).css("marginLeft"))+parseInt(t(this).css("marginRight"))});var n=e.tab.width();if(a+t("#tabManage").outerWidth()+36>n){if(t(".l-tab-links-left",e.tab.links).length<1){e.tab.links.css({margin:"0 0 -1px"});e.tab.links.append('<div class="l-tab-links-left"><i></i></div><div class="l-tab-links-right"><i></i></div>');e.setTabButtonEven()}return true}else{e.tab.links.ul.animate({left:0});e.tab.links.css({margin:"0 18px -1px"});t(".l-tab-links-left,.l-tab-links-right",e.tab.links).remove();return false}},setTabButtonEven:function(){var e=this,i=this.options;t(".l-tab-links-left",e.tab.links).hover(function(){t(this).addClass("l-tab-links-left-over")},function(){t(this).removeClass("l-tab-links-left-over")}).click(function(){e.moveToPrevTabItem()});t(".l-tab-links-right",e.tab.links).hover(function(){t(this).addClass("l-tab-links-right-over")},function(){t(this).removeClass("l-tab-links-right-over")}).click(function(){e.moveToNextTabItem()})},moveToPrevTabItem:function(){var e=this,i=this.options;var a=t(".l-tab-links-left",e.tab.links).outerWidth();var n=new Array;t("li",e.tab.links).each(function(e,i){var r=-1*a;if(e>0){var l=t(this).prev();r=parseInt(n[e-1])+l.outerWidth()+parseInt(l.css("marginLeft"))+parseInt(l.css("marginRight"))}n.push(r)});var r=-1*parseInt(e.tab.links.ul.css("left"));for(var l=0;l<n.length-1;l++){if(n[l]<r&&n[l+1]>=r){e.tab.links.ul.animate({left:-1*parseInt(n[l])},100);return}}},moveToNextTabItem:function(){var e=this,i=this.options;var a=t(".l-tab-links-right",e.tab.links).outerWidth();var n=0;var r=t("li",e.tab.links.ul);r.each(function(){n+=t(this).outerWidth()+parseInt(t(this).css("marginLeft"))+parseInt(t(this).css("marginRight"))});var l=e.tab.links.width();var s=new Array;for(var o=r.length-1;o>=0;o--){var d=n-l+a;if(o!=r.length-1){d=parseInt(s[r.length-2-o])-(t(r[o+1]).outerWidth()+parseInt(t(r[o+1]).css("marginLeft"))+parseInt(t(r[o+1]).css("marginRight")))}s.push(d)}var b=-1*parseInt(e.tab.links.ul.css("left"));for(var c=1;c<s.length;c++){if(s[c]<=b&&s[c-1]>b){e.tab.links.ul.animate({left:-1*parseInt(s[c-1])});return}}},getTabItemCount:function(){var e=this,i=this.options;return t("li",e.tab.links.ul).length},getSelectedTabItemID:function(){var e=this,i=this.options;return t("li.l-selected",e.tab.links.ul).attr("tabid")},removeSelectedTabItem:function(){var t=this,e=this.options;t.removeTabItem(t.getSelectedTabItemID())},overrideSelectedTabItem:function(t){var e=this,i=this.options;e.overrideTabItem(e.getSelectedTabItemID(),t)},overrideTabItem:function(e,i){var a=this,n=this.options;if(a.trigger("beforeOverrideTabItem",[e])==false)return false;var r=i.tabid;if(r==undefined)r=a.getNewTabid();var l=i.url;var s=i.content;var o=i.target;var d=i.text;var b=i.showClose;var c=i.height;var f=t("li[tabid="+e+"]",a.tab.links.ul);var h=t(".l-tab-content-item[tabid="+e+"]",a.tab.content);var u=t("div:first",h).show();if(!f||!h)return;f.attr("tabid",r);h.attr("tabid",r);if(t("iframe",h).length==0&&l){h.html("<iframe frameborder='0'></iframe>")}else if(s){h.html(s)}t("iframe",h).attr("name",r);if(b==undefined)b=true;if(b==false)t(".l-tab-links-item-close",f).remove();else{if(t(".l-tab-links-item-close",f).length==0)f.append("<div class='l-tab-links-item-close'></div>")}if(d==undefined)d=r;if(c)h.height(c);t("a",f).text(d);t("iframe",h).attr("src",l).bind("load.tab",function(){u.hide();if(i.callback)i.callback()});a.trigger("afterOverrideTabItem",[e])},selectTabItem:function(e){var i=this,a=this.options;if(i.trigger("beforeSelectTabItem",[e])==false)return false;i.trigger("afterLeaveTabItem",[i.selectedTabId]);i.selectedTabId=e;t("> .l-tab-content-item[tabid="+e+"]",i.tab.content).show().siblings().hide();t("li[tabid="+e+"]",i.tab.links.ul).addClass("l-selected").siblings().removeClass("l-selected");i.trigger("afterSelectTabItem",[e])},moveToLastTabItem:function(){var e=this,i=this.options;var a=0;t("li",e.tab.links.ul).each(function(){a+=t(this).outerWidth()+parseInt(t(this).css("marginLeft"))+parseInt(t(this).css("marginRight"))});var n=e.tab.width();if(a>n){var r=t(".l-tab-links-right",e.tab.links).outerWidth();e.tab.links.ul.animate({left:-1*(a-n+t("#tabManage").outerWidth()+r)},100)}},isTabItemExist:function(e){var i=this,a=this.options;return t("li[tabid="+e+"]",i.tab.links.ul).length>0},addTabItem:function(e){var i=this,a=this.options;var n=e.tabid;if(i.trigger("beforeAddTabItem",[n])==false)return false;if(n==undefined)n=i.getNewTabid();var r=e.url;var l=e.content;var s=e.text;var o=e.showClose;var d=e.height;var b=e.callBack;if(i.isTabItemExist(n)){var c=t(".l-tab-content-item[tabid="+n+"]").find("iframe").attr("src");i.selectTabItem(n);if(c!=r){i.overrideTabItem(n,e);return}if(e.callback){e.callback()}return}var f=t("<li><a></a><div class='l-tab-links-item-left'></div><div class='l-tab-links-item-right'></div><div class='l-tab-links-item-close'></div></li>");var h=t("<div class='l-tab-content-item'><div class='l-tab-loading' style='display:block;'></div><iframe frameborder='0'></iframe></div>");var u=t("div:first",h);var v=t("iframe:first",h);if(i.makeFullHeight){var g=i.tab.height()-i.tab.links.height();h.height(g)}f.attr("tabid",n);h.attr("tabid",n);if(r){v.attr("name",n).attr("id",n).attr("src",r).bind("load.tab",function(){u.hide();if(e.callback)e.callback()})}else{v.remove();u.remove()}if(l){h.html(l)}else if(e.target){h.append(e.target)}if(o==undefined)o=true;if(o==false)t(".l-tab-links-item-close",f).remove();if(s==undefined)s=n;if(d)h.height(d);t("a",f).text(s);i.tab.content.append(h);if(t("#tabManage").length===0){i.tab.links.ul.append(f);i.tab.links.ul.append('<li id="tabManage"><i></i></li>');i.setTabManageEven()}else{f.insertBefore("#tabManage")}i.selectTabItem(n);if(i.setTabButton()){i.moveToLastTabItem()}i._addTabItemEvent(f);if(a.dragToMove&&t.fn.ligerDrag){i.drags=i.drags||[];f.each(function(){i.drags.push(i._applyDrag(this))})}i.trigger("afterAddTabItem",[n])},_addTabItemEvent:function(e){var i=this,a=this.options;e.click(function(){var e=t(this).attr("tabid");i.selectTabItem(e)});i.tab.menu&&i._addTabItemContextMenuEven(e);t(".l-tab-links-item-close",e).hover(function(){t(this).addClass("l-tab-links-item-close-over")},function(){t(this).removeClass("l-tab-links-item-close-over")}).click(function(){var e=t(this).parent().attr("tabid");i.removeTabItem(e)})},removeTabItem:function(e,i){var a=this,n=this.options;if(typeof e!="string"){e=e.toString()}if(!i&&a.trigger("beforeRemoveTabItem",[e])==false)return false;var r=t("li[tabid="+e+"]",a.tab.links.ul).hasClass("l-selected");if(r){var l=t(".l-tab-content-item[tabid="+e+"]",a.tab.content).prev().attr("tabid");a.selectTabItem(l)}t(".l-tab-content-item[tabid="+e+"]",a.tab.content).remove();t("li[tabid="+e+"]",a.tab.links.ul).remove();a.setTabButton();a.trigger("afterRemoveTabItem",[e])},addHeight:function(t){var e=this,i=this.options;var a=e.tab.height()+t;e.setHeight(a)},setHeight:function(t){var e=this,i=this.options;e.tab.height(t);e.setContentHeight()},setContentHeight:function(){var e=this,i=this.options;var a=e.tab.height()-e.tab.links.height();e.tab.content.height(a);t("> .l-tab-content-item",e.tab.content).height(a)},getNewTabid:function(){var t=this,e=this.options;t.getnewidcount=t.getnewidcount||0;return"tabitem"+ ++t.getnewidcount},getTabidList:function(e,i){var a=this,n=this.options;var r=[];t("> li",a.tab.links.ul).each(function(){if(t(this).attr("tabid")&&t(this).attr("tabid")!=e&&(!i||t(".l-tab-links-item-close",this).length>0)){r.push(t(this).attr("tabid"))}});return r},removeOther:function(e,i){var a=this,n=this.options;var r=a.getTabidList(e,true);t(r).each(function(){a.removeTabItem(this)})},reload:function(e,i){var a=this,n=this.options;var r=t(".l-tab-content-item[tabid="+e+"]");var l=t(".l-tab-loading:first",r);var s=t("iframe:first",r);var o=t(s).attr("src");l.show();s.attr("src",o).unbind("load.tab").bind("load.tab",function(){l.hide();if(typeof i==="function"){i()}})},removeAll:function(e){var i=this,a=this.options;var n=i.getTabidList(null,true);t(n).each(function(){i.removeTabItem(this)})},onResize:function(){var e=this,i=this.options;if(!i.height||typeof i.height!="string"||i.height.indexOf("%")==-1)return false;if(e.tab.parent()[0].tagName.toLowerCase()=="body"){var a=t(window).height();a-=parseInt(e.tab.parent().css("paddingTop"));a-=parseInt(e.tab.parent().css("paddingBottom"));e.height=i.heightDiff+a*parseFloat(e.height)*.01}else{e.height=i.heightDiff+e.tab.parent().height()*parseFloat(i.height)*.01}e.tab.height(e.height);e.setContentHeight()},_menuItemClick:function(e){var i=this,a=this.options;i.actionTabid=i.actionTabid||i.getSelectedTabItemID();if(!e.id||!i.actionTabid)return;switch(e.id){case"close":i.removeTabItem(i.actionTabid);i.actionTabid=null;break;case"closecur":if(i.getSelectedTabItemID()==="index"){break}i.removeTabItem(i.getSelectedTabItemID());break;case"closeother":i.removeOther(i.actionTabid);break;case"closeall":i.removeAll();i.actionTabid=null;break;case"reload":i.selectTabItem(i.actionTabid);i.reload(i.actionTabid);break;case"reloadcur":i.reload(i.getSelectedTabItemID());break;case"reloadall":var n=i.getTabidList(null,false);t(n).each(function(){i.reload(this)});break}},_addTabItemContextMenuEven:function(e){var i=this,a=this.options;e.bind("contextmenu",function(a){if(!i.tab.menu)return;i.actionTabid=e.attr("tabid");i.tab.menu.show({top:a.pageY,left:a.pageX});if(t(".l-tab-links-item-close",this).length==0){i.tab.menu.setDisabled("close")}else{i.tab.menu.setEnabled("close")}return false})}})})(jQuery);