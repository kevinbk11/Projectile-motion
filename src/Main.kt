import GUI.PhysicsDrawing
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Polygon
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.*

class drawPanel(var x:MutableList<Double>,var y:MutableList<Double>,var theta:Double):JPanel(){
    override fun paint(g:Graphics)
    {
        super.paint(g)
        g.font=Font("TimesRoman",Font.PLAIN,18)
        if(y[0]<=0)theta=45.00
        g.drawString("${theta.toFloat()}度時有最遠射程",x.max()!!.toInt()-20,20)
        g.drawString("最遠射程${x.max()!!.toFloat()/10}",x.max()!!.toInt()-20,40)
        for(i in 0..x.lastIndex-1)
        {
            g.drawLine((x[i]).toInt(),(y.max()!!-y[i]+40).toInt(),(x[i+1]).toInt(),(y.max()!!-y[i+1]+40).toInt())
        }
        g.color= Color.BLACK
    }
}
fun main(args:Array<String>)
{
    val j = JFrame("")
    val p = PhysicsDrawing()
    var j2 = JFrame("結果")
    j.defaultCloseOperation=JFrame.EXIT_ON_CLOSE
    p.button1.addActionListener {
        val v =p.v.text.toDouble()
        var h=0.0
        if(p.h.text!="")
        {
            h =p.h.text.toDouble()
        }
        var bigXL = mutableListOf<Double>()
        var bigYL = mutableListOf<Double>()
        var ans=0.0
        var bigX = 0.0
        var theta = 0.0
        var dtheta = 0.01
        while(theta<90.0)
        {
            var yL = 0.1
            var i =0.000
            val x= mutableListOf<Double>()
            val y= mutableListOf<Double>()
            val vx = cos(3.14159/180*theta)*v
            val vy = sin(3.14159/180*theta)*v
            while(yL>=0)
            {
                yL=-4.9*i*i+vy*i+h
                if(yL<0)break
                x.add(i*vx*50)
                y.add(yL*50)
                i+=0.001
            }
            if(x.last()>bigX)
            {
                bigX=x.last()
                bigXL=x
                bigYL=y
                ans=theta
            }
            theta+=dtheta
        }
        println(ans)
        j2.isVisible=false
        j2 = JFrame("結果")
        with(j2)
        {
            setSize(bigXL.max()!!.toInt()+180,(bigYL.max()!!).toInt()+80)
            setLocation(200,0)
            isVisible=true
            isResizable=false
            add(drawPanel(bigXL,bigYL,ans))
        }
    }
    j.setSize(200,80)
    j.isVisible=true
    j.isResizable=false
    j.contentPane=p.Panel
}