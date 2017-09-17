package matsu_chara.quill_free.quill

import io.getquill.monad.Effect
import RoleDb.{Role, RoleCheck}

class RoleDb[R <: Role](configPrefix: String) {
  implicit val ctx: MyDbContext = new MyDbContext

  def roleBasedPerformIO[T, E <: Effect](op: ctx.IO[T, E])(implicit ev: RoleCheck[R, E]): T = {
    ctx.performIO[T](op)
  }

  def close(): Unit = {
    ctx.close()
  }
}

object RoleDb {
  sealed trait Role
  trait Master extends Role
  trait Slave extends Role

  trait RoleCheck[D <: Role, E <: Effect]
  implicit val slaveRead: RoleCheck[Slave, Effect.Read] = null
  implicit val masterRead: RoleCheck[Master, Effect.Read] = null
  implicit val masterWrite: RoleCheck[Master, Effect.Write] = null
  implicit val masterReadWriteTransaction: RoleCheck[Master, Effect.Read with Effect.Write with Effect.Transaction] =
    null
}
