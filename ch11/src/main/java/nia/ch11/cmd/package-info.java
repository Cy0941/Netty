/**
 * Function: 自定义基于分隔符的解码器及解析<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 23:39 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 *
 * 1、传入数据流是一系列帧，每个帧都由换行符(\n)分隔
 * 2、每个帧都由一系列的元素组成，每个元素都由单个空格字符分隔
 * 3、一个帧的内容代表一个命令，定义为一个命令名称后跟着数目可变的参数
 *
 */
package nia.ch11.cmd;