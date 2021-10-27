import GUI.PhysicsDrawing
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.*

class drawPanel(var x:MutableList<Double>,var y:MutableList<Double>,var theta:Float):JPanel(){
    override fun paint(g:Graphics)
    {
        super.paint(g)
        g.font=Font("TimesRoman",Font.PLAIN,18)
        g.drawString("${theta}度時有最遠射程",x.max()!!.toInt()-50,20)
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
        val D = p.C.text.toDouble()*p.P.text.toDouble()*p.A.text.toDouble()/2
        val m = p.m.text.toDouble()
        var h=0.0
        if(p.h.text!="")
        {
            h =(p.h.text.toDouble()/100)
        }
        var bigXL = mutableListOf<Double>()
        var bigYL = mutableListOf<Double>()
        var ans=0.0F
        var bigX = -1.0
        val dt = 0.001
        var theta = 0.0F
        //----------模擬過程-----------//
        while(theta!=90.0F)
        {
            var i =0.000
            val x= mutableListOf<Double>()
            val y= mutableListOf<Double>()
            val vx = cos(3.1415926535/180*theta)*v
            val vy = sin(3.1415926535/180*theta)*v
            var lastX=0.0
            var lastY:Double=h
            var lastVx:Double=vx
            var lastVy:Double=vy
            var lastAx=0.0
            var lastAy=0.0
            val dm = -(D/m)
            while(lastY>=0.0)
            {
                val ax=dm*abs(lastVx)*lastVx
                val ay=dm*abs(lastVy)*lastVy-9.8
                val Vx = lastVx+lastAx*dt
                val Vy = lastVy+lastAy*dt
                val xl = lastX+lastVx*dt+lastAx*dt*dt/2
                val yl = lastY+lastVy*dt+lastAy*dt*dt/2
                if(lastY<0.0)break
                x.add(xl*10)
                y.add(yl*10)
                i+=dt
                lastAx = ax
                lastAy = ay
                lastVx = Vx
                lastVy = Vy
                lastX = xl
                lastY = yl
            }
            if(x.last()<bigX)
            {
                ans=theta
                break
            }
            else
            {
                bigX=x.last()
                bigXL=x
                bigYL=y
            }
            theta+=(dt.toFloat()*10F)
        }
        //----------模擬過程---------//
        println(ans)
        println(bigXL.max())
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
    j.setSize(200,200)
    j.isVisible=true
    j.isResizable=false
    j.contentPane=p.Panel
}