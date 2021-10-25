import GUI.PhysicsDrawing
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.*

class drawPanel(var x:MutableList<Double>,var y:MutableList<Double>):JPanel(){
    override fun paint(g:Graphics)
    {
        super.paint(g)
        for(i in 0..x.lastIndex-1)
        {
            g.drawLine((x[i]).toInt(),1080-(y[i]).toInt(),(x[i+1]).toInt(),1080-(y[i+1]).toInt())
        }
    }
}
fun main(args:Array<String>)
{
    val j = JFrame("test")
    val p = PhysicsDrawing()
    var j2 = JFrame("結果")
    p.button1.addActionListener {
        val v =p.v.text.toDouble()
        var h=0
        if(p.h.text!="")
        {
            h =p.h.text.toInt()
        }
        var bigXL = mutableListOf<Double>()
        var bigYL = mutableListOf<Double>()
        var ans:Int=0
        var bigX = 0.0
        for(theta in 0..90)
        {
            var yL = 0.1
            var i =0.001
            val x= mutableListOf<Double>()
            val y= mutableListOf<Double>()
            val vx = cos(3.14159/180*theta)*v
            val vy = sin(3.14159/180*theta)*v
            while(yL>0)
            {
                x.add(i*vx*10)
                yL=-4.9*i*i+vy*i+h/100.0
                y.add(yL*(60+sqrt((h/100).toFloat())))
                i+=0.001
            }
            if(x.last()>bigX)
            {
                bigX=x.last()
                bigXL=x
                bigYL=y
                ans=theta
            }
        }
        println(ans)
        j2.isVisible=false
        j2 = JFrame("結果")
        with(j2)
        {

            setSize(1920,1080)
            isVisible=true
            isResizable=false
            add(drawPanel(bigXL,bigYL))
        }
    }
    j.setSize(200,100)
    j.isVisible=true
    j.isResizable=false
    j.contentPane=p.Panel
}