import GUI.PhysicsDrawing
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Polygon
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.*

class drawPanel(var x:MutableList<Double>,var y:MutableList<Double>,var theta:Int):JPanel(){
    override fun paint(g:Graphics)
    {
        super.paint(g)
        g.font=Font("TimesRoman",Font.PLAIN,18)
        g.drawString("${theta}度時有最遠射程",x.max()!!.toInt()-20,20)
        for(i in 0..x.lastIndex-1)
        {
            g.drawLine((x[i]).toInt(),(y.max()!!-y[i]).toInt(),(x[i+1]).toInt(),(y.max()!!-y[i+1]).toInt())
        }
        g.color= Color.BLACK
    }
}
fun main(args:Array<String>)
{
    val j = JFrame("")
    val p = PhysicsDrawing()
    var j2 = JFrame("結果")
    p.button1.addActionListener {
        val v =p.v.text.toDouble()
        val D = p.D.text.toDouble()
        val m = p.m.text.toDouble()
        var h=0.0
        if(p.h.text!="")
        {
            h =(p.h.text.toInt()/10).toDouble()
        }
        var bigXL = mutableListOf<Double>()
        var bigYL = mutableListOf<Double>()
        var ans=0
        var bigX = 0.0
        for(theta in 0..90)
        {
            var i =0.000
            val x= mutableListOf<Double>()
            val y= mutableListOf<Double>()
            val vx = cos(3.14159/180*theta)*v
            val vy = sin(3.14159/180*theta)*v
            var lastX:Double=0.0
            var lastY:Double=h.toDouble()
            var lastVx:Double=vx
            var lastVy:Double=vy
            var lastAx:Double=0.0
            var lastAy:Double=0.0
            val dm = -(D/m)
            while(lastY>=0.0)
            {
                val ax=dm*abs(lastVx)*lastVx
                val ay=dm*abs(lastVy)*lastVy-9.8
                val Vx = lastVx+lastAx*0.001
                val Vy = lastVy+lastAy*0.001
                val xl = lastX+lastVx*0.001+lastAx*0.001*0.001/2
                val yl = lastY+lastVy*0.001+lastAy*0.001*0.001/2
                if(yl<0.0)break
                x.add(xl*10)
                y.add(yl*10)
                i+=0.001
                lastAx = ax
                lastAy = ay
                lastVx = Vx
                lastVy = Vy
                lastX = xl
                lastY = yl
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
            setSize(bigXL.max()!!.toInt()+180,(bigYL.max()!!).toInt()+40)
            setLocation(200,0)
            isVisible=true
            isResizable=true
            add(drawPanel(bigXL,bigYL,ans))
        }
    }
    j.setSize(200,100)
    j.isVisible=true
    j.isResizable=false
    j.contentPane=p.Panel
}